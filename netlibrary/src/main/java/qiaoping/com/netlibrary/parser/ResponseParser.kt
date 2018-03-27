package qiaoping.com.netlibrary.parser

import android.text.TextUtils
import com.google.gson.Gson
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.Util.UTF_8
import org.json.JSONObject
import qiaoping.com.netlibrary.assist.HttpHandler
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.request.HttpRequest
import java.io.IOException

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
abstract class ResponseParser<T> {
    /**网络请求的request*/
    protected lateinit var request:HttpRequest<T>
    /**返回字符串结果时的内容长度*/
    protected var contentLength:Long = 0
    /**当次请求的编码名称*/
    protected lateinit var charsetName:String

    protected val mGson:Gson = Gson()

    constructor(request: HttpRequest<T>){
        this.request=request
    }

    /**
     * 解析OkHttp请求的结果流，并返回解析得到的实体对象
     * @param response	待解析的响应结果
     */
    abstract fun parser(response:Response): ResponseData

    /**
     * 解析OkHttp同步请求响应，此请求返回单个实体对象
     * @param response				待解析的请求响应体
     * @return						解析成功返回{@link List}
     */
    fun parserSync(response: Response):ResponseData{
        if (response.isSuccessful){
            try {
            getResponseBodyInfo(response.body())
            return parser(response)
            }catch (e:Exception){
                return ResponseData(false,"数据解析异常",null)
            }
        }else{
            return ResponseData(false,"响应错误",null)
        }
    }

    /**
     * 解析OkHttp异步请求响应
     * @param response		待解析的请求响应体
     */

    fun parserAsync(response: Response?){
        if (response==null){
            val responseData = ResponseData(false,"无数据返回",null)
            HttpHandler.get().onFailed(responseData,request.mUserCallBack)
            return
        }
        if (response.isSuccessful) {
            try {
                getResponseBodyInfo(response.body())
                val data = parser(response)
                //登录拦截
                if (request.isLoginIntercept) {
                    if (TextUtils.isEmpty(data.code)) {
                        try {
                            val jsonObject = JSONObject(data.data.toString())
                            val code = jsonObject.getString("code")
                            if (code == "-401") {
                                HttpHandler.get().reLogin(request)
                                HttpHandler.get().onFailed(data, request.mUserCallBack)
                                //TODO 储存用户信息
//                                LoginBlock.setUserInfo(null)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()

                        }

                    } else {
                        if (data.code.equals("-401")) {
                            HttpHandler.get().reLogin(request)
                            HttpHandler.get().onFailed(data, request.mUserCallBack)
                            //TODO 储存用户信息
//                            LoginBlock.setUserInfo(null)
                        }
                    }
                }

                HttpHandler.get().onSuccess(data, request.mUserCallBack)
                //                if(data.retCode != null && data.retCode.equals("E00006")){
                //                    //token过期
                //                    HttpHandler.get().reLogin(request);
                //                }
            } catch (e: IOException) {
                val data = ResponseData(false, "数据接收异常", null)
                HttpHandler.get().onFailed(data, request.mUserCallBack)
            } catch (e: Exception) {
                val data = ResponseData(false, "数据解析异常", null)
                HttpHandler.get().onFailed(data, request.mUserCallBack)
            }

        } else {
            val data = ResponseData(false, "响应错误", null)
            HttpHandler.get().onFailed(data, request.mUserCallBack)
        }

    }

    /**
     * 获取此次请求的编码方式和内容长度
     * @param body
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun getResponseBodyInfo(body: ResponseBody?) {
        if (body==null) return
        val contentType = body.contentType()
        val charset = if (contentType != null) contentType.charset(UTF_8) else UTF_8
        if (charset != null) {
            this.charsetName= charset.name()
        }
        this.contentLength = body.contentLength()
    }


}