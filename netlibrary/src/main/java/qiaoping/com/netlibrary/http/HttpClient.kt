package qiaoping.com.netlibrary.http

import okhttp3.*
import qiaoping.com.common.block.LoginBlock
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.assist.HttpHandler
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.cookies.AddCookiesInterceptor
import qiaoping.com.netlibrary.cookies.CookieJarManager
import qiaoping.com.netlibrary.interceptor.LoggerInterceptor
import qiaoping.com.netlibrary.cookies.ReceivedCookiesInterceptor
import qiaoping.com.netlibrary.request.HttpRequest
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:OkHttpRequest网络请求部分
 */
class HttpClient {
    //ok http client
    private var okClient: OkHttpClient
    private var call: Call? = null

    /**
     * init
     */
    companion object {
        private var uniqueInstance: HttpClient? = null
        fun init(config: HttpConfig) {
            if (uniqueInstance == null) {
                synchronized(HttpClient::class.java) {
                    if (uniqueInstance == null) {
                        uniqueInstance = HttpClient(config)
                    }
                }
            } else {
                uniqueInstance!!.initInner(config)
            }
        }

        /**
         * 初始化当前对象
         *
         * @return
         */
        fun instance(): HttpClient? {
            if (uniqueInstance != null)
                return uniqueInstance as HttpClient
            synchronized(HttpClient::class.java) {
                if (uniqueInstance == null) {
                    uniqueInstance = HttpClient()
                }
            }
            return uniqueInstance
        }
    }

    /**
     * constructor
     */
    constructor(){
        val build = OkHttpClient.Builder()
        build.connectTimeout(HttpConfig.connectTimeOut.toLong(), TimeUnit.SECONDS)
        build.readTimeout(HttpConfig.readTimeOut.toLong(), TimeUnit.SECONDS)
        build.writeTimeout(HttpConfig.writeTimeOut.toLong(), TimeUnit.SECONDS)
        build.addInterceptor(LoggerInterceptor())
        okClient = build.build()


        okClient.interceptors().add(AddCookiesInterceptor())
        okClient.interceptors().add(ReceivedCookiesInterceptor())

    }

    /**
     * constructor
     */
    constructor(config: HttpConfig?){
        val build = OkHttpClient.Builder()
        build.connectTimeout(HttpConfig.connectTimeOut.toLong(), TimeUnit.SECONDS)
        build.readTimeout(HttpConfig.readTimeOut.toLong(), TimeUnit.SECONDS)
        build.writeTimeout(HttpConfig.writeTimeOut.toLong(), TimeUnit.SECONDS)
        build.addInterceptor(LoggerInterceptor())
        if (config != null) {
            if (config.supportCookie) {
                //                build.setCookieHandler(config.cookieHandler);
                build.cookieJar(config.cookieJarManager)
            }
            if (config.isSetCacheSize) {
                build.cache(Cache(File(config.cacheDirectory), config.cacheSize))
            }
        }
        okClient = build.build()
    }

    /**
     * init
     */
    private fun initInner(config: HttpConfig?) {
            val build = OkHttpClient.Builder()
            build.connectTimeout(HttpConfig.connectTimeOut.toLong(), TimeUnit.SECONDS)
            build.readTimeout(HttpConfig.readTimeOut.toLong(), TimeUnit.SECONDS)
            build.writeTimeout(HttpConfig.writeTimeOut.toLong(), TimeUnit.SECONDS)
            build.addInterceptor(LoggerInterceptor())
            if (config != null) {
                if (config.supportCookie) {
                    //                    okClient.setCookieHandler(config.cookieHandler);
                    build.cookieJar(config.cookieJarManager)
                }
                if (config.isSetCacheSize) {
                    build.cache(Cache(File(config.cacheDirectory), config.cacheSize))
                }
            }
            okClient = build.build()
    }

    /**
     * 异步网络请求
     *
     * @param httpRequest
     * @return
     */
    fun <T> commitAsync(httpRequest: HttpRequest<T>): Call? {
        call = null
        try {
            val okRequest:Request?= httpRequest.build()
            if (okRequest == null) {
                val data = ResponseData(false, "请求参数错误", null)
                HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())
                return null
            }
            HttpHandler.get().onStart(httpRequest.getUserCallBack())
            call = okClient.newCall(okRequest)
            httpRequest.setCall(call!!)
            call!!.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val data = ResponseData(false, "网络不给力，请再试一次", null)
                    HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())
                    LogUtil.makeLog("HttpClient", "========request onFailure")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response?) {
                    if (response == null) {
                        val data = ResponseData(false, "响应为空", null)
                        HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())
                        return
                    }
                    LogUtil.makeLog("HttpClient", "=====onResponse result:" + response.isSuccessful)
                    if (!response.isSuccessful) {
                        LogUtil.makeLog("HttpClient", "======error msg:" + response.message())
                        val data = ResponseData(false, "响应错误", null)
                        HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())

                    } else {
                        val authToken = response.header("x-auth-token")
                        if (authToken != null) {
                            LogUtil.makeLog("httpClient", "============= authToken:$authToken")
                            LoginBlock.setAuthToken(authToken)
                        }

                        if (httpRequest.getParser() != null) {
                            httpRequest.getParser().parserAsync(response)
                        } else {
                            val data = ResponseData(false, "无法解析数据", null)
                            HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            val data = ResponseData(false, "请求异常", null)
            HttpHandler.get().onFailed(data, httpRequest.getUserCallBack())
        }

        return call
    }

    /**
     * 同步网络请求
     *
     * @param request
     * @return
     */
    fun <T> commitSync(request: HttpRequest<T>): ResponseData {
        val data = ResponseData()
        try {
            val parser = request.getParser()
            val okRequest = request.build()
            if (okRequest == null) {
                data.status = false
                data.message = "参数错误"
                return data
            }
            call = okClient.newCall(okRequest)
            val response = call!!.execute()
            if (response.isSuccessful) {
                return parser.parserSync(response)
                //                return response.body().string();
            }
            data.status = false
            data.message = "请求失败"
        } catch (e: Exception) {
            e.printStackTrace()
            data.status = false
            data.message = "请求异常"
        }

        return data
    }

    /**
     * 立即停掉当前执行的请求
     */
    fun cancelRequestNow() {
        if (call != null) {
            call!!.cancel()
        }
    }

    fun getOkClient(): OkHttpClient {
        return okClient
    }

    /**
     * 清除cookies数据
     */
    fun clearCookies() {
        if (okClient.cookieJar() != null) {
            val jarManager = okClient.cookieJar() as CookieJarManager
            jarManager.clearCookies()
        }
    }
}