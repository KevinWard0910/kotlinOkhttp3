package qiaoping.com.netlibrary.request

import android.content.Context
import okhttp3.Request
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.parser.DownloadResponseParser
import qiaoping.com.netlibrary.parser.ResponseParser
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.HashMap

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class HttpDownloadRequest<T>:HttpRequest<T> {

    private lateinit var mSaveFilePath:String
    private lateinit var mDownloadParser:DownloadResponseParser<T>

    constructor(context: Context):super(context)

    fun path(filePath:String):HttpDownloadRequest<T>{
        mSaveFilePath = filePath
        return this
    }



    override fun build(): Request? {
        val sb = StringBuilder(mUrl)
        if (mParams.size > 0) {
            val map = HashMap<String, String>()
            for (key in mParams.keys) {
                if (mParams[key] == null) {
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
            if (!sb.toString().contains("?")) {
                sb.append("?")
            }
            if (!sb.toString().endsWith("?")) {
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
        return Request.Builder().url(url).cacheControl(getCacheControl()).build()
    }

    override fun getParser(): ResponseParser<*> {
        if (mDownloadParser == null) {
            mDownloadParser = DownloadResponseParser(this, mSaveFilePath)
        }
        return mDownloadParser
    }
}