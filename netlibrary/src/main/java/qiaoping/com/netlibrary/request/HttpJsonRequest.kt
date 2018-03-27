package qiaoping.com.netlibrary.request

import android.content.Context
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.parser.JsonResponseParser
import qiaoping.com.netlibrary.parser.ResponseParser
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.HashMap

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description: 网络请求
 */
class HttpJsonRequest<T>: HttpRequest<T> {

    private var isPostJson = false
    private lateinit var mParser:ResponseParser<*>
    private val mGson:Gson= Gson()

    companion object {
        val JSON:MediaType = MediaType.parse("application/json; charset=utf-8")!!

    }

    constructor(context: Context):super(context)

    override fun build(): Request? {
        var request: Request? = null
        if (mUrl == null) {
            return null
        }
        if (appendDefaultParam) {
            appendDefaultParams()
        }
        if (appendDefaultHeader) {
            appendDefaultHeaders()
        }


        when (mMethod) {
            Method.METHOD_GET -> request = requestGet()
            Method.METHOD_POST -> request = requestPost()
            Method.METHOD_DELETE -> request = requestDelete()
            Method.METHOD_PUT -> request = requestPut()
            else -> {
            }
        }
        return request!!
    }

    override fun getParser(): ResponseParser<*> {
        if (mParser == null) {
            mParser = JsonResponseParser<T>(this)
        }
        return mParser
    }

    /**
     * 是否以json格式post数据
     */
    fun postWithJsonFormat(value: Boolean): HttpJsonRequest<*> {
        this.isPostJson = value
        return this
    }

    /**
     * get请求
     */
    private fun requestGet(): Request {
        val sb = StringBuilder(mUrl)
        if (mParams != null && mParams.size> 0) {
            val map = HashMap<String, String>()
            for (key in mParams.keys) {
                if (key == null || mParams[key] == null) {
                    continue
                }
                var value = mParams[key].toString()
                try {
                    value = URLEncoder.encode(value, "utf-8")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                map[key] = value
            }
            if (sb.toString().contains("?") == false) {
                sb.append("?")
            }
            if (sb.toString().endsWith("?") == false) {
                sb.append("&")
            }
            if (secret) {
                //                SignUtil.createStringSignMap(map);
            }
            var position = 0
            for (key in map.keys) {
                sb.append(key + "=" + map[key])
                if (position < map.size - 1) {
                    sb.append("&")
                }
                position++
            }
        }
        val url = sb.toString()
        LogUtil.makeLog("HttpJsonRequest", "=========get url:$url")
        val builder = Request.Builder()
        if (mHeaders != null) {
            for (key in mHeaders.keys) {
                builder.addHeader(key, mHeaders[key])
            }
        }
        return builder.url(url).cacheControl(getCacheControl()).build()
    }

    /**
     * Post请求
     */
    private fun requestPost(): Request {
        var body: RequestBody? = null
        if (isPostJson) {
            body = buildJsonRequestBody()
        } else {
            body = buildFormRequestBody()
        }
        val builder = Request.Builder()
        if (mHeaders != null) {
            for (key in mHeaders.keys) {
                builder.addHeader(key, mHeaders[key])
            }
        }
        return builder.url(mUrl).post(body!!).cacheControl(getCacheControl()).build()
    }

    /**
     * put请求
     */
    private fun requestPut(): Request {
        var body: RequestBody? = null
        if (isPostJson) {
            body = buildJsonRequestBody()
        } else {
            body = buildFormRequestBody()
        }
        val builder = Request.Builder()
        if (mHeaders != null) {
            for (key in mHeaders.keys) {
                builder.addHeader(key, mHeaders[key])
            }
        }
        return builder.url(mUrl).put(body!!).build()
    }

    /**
     * delete请求
     */
    private fun requestDelete(): Request {
        var body: RequestBody? = null
        if (isPostJson) {
            body = buildJsonRequestBody()
        } else {
            body = buildFormRequestBody()
        }
        val builder = Request.Builder()
        if (mHeaders != null) {
            for (key in mHeaders.keys) {
                builder.addHeader(key, mHeaders[key])
            }
        }
        return builder.url(mUrl).delete(body).build()
    }

    /**
     * 生成表单形式的requestbody
     */
    private fun buildFormRequestBody(): RequestBody? {
        var body: RequestBody? = null
        if (secret) {
            //            SignUtil.createSignMap(mParams);
        }
        if (mParams != null && mParams.size > 0) {
            //            MultipartBody.Builder formBuild = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //            for(String key : mParams.keySet()){
            //                LogUtil.makeLog("HttpJsonRequest","========= params:key:"+key+" value:"+mParams.get(key));
            //                formBuild.addFormDataPart(key, mParams.get(key).toString());
            //            }
            //            body = formBuild.build();

            val formBuild = FormBody.Builder()
            for (key in mParams.keys) {
                formBuild.add(key, mParams[key].toString())
                LogUtil.makeLog("HttpJsonRequest", "========= params:key:" + key + " value:" + mParams[key])
            }
            body = formBuild.build()
        }
        return body
    }

    /**
     * 生成json形式的requestbody
     */
    private fun buildJsonRequestBody(): RequestBody? {
        var body: RequestBody? = null
        if (secret) {
            //            SignUtil.createSignMap(mParams);
        }
        if (mParams != null && mParams.size > 0) {
            val json = mGson.toJson(mParams)
//            val json = JsonUtil.objectToString(mParams)
            LogUtil.makeLog("HttpJsonRequest", "======post json:$json")
            body = RequestBody.create(JSON, json)
        }
        return body
    }
}