package qiaoping.com.netlibrary.request

import android.content.Context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.assist.HttpFile
import qiaoping.com.netlibrary.parser.ResponseParser
import qiaoping.com.netlibrary.parser.UploadResponseParser
import java.io.File
import java.net.URLConnection

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class HttpUploadRequest<T>:HttpRequest<T> {

    private lateinit var mParser:UploadResponseParser<T>
    private var mHttpFileList:ArrayList<HttpFile> = ArrayList()

    constructor(context: Context) : super(context)

    override fun build(): Request? {
        //default params
        if (appendDefaultParam) {
            appendDefaultParams()
        }

        if (appendDefaultHeader) {
            appendDefaultHeaders()
        }

        val multipart = MultipartBody.Builder()
        multipart.setType(MultipartBody.FORM)
        //表单参数
        if (mParams != null && mParams.size > 0) {
            for (key in mParams.keys) {
                multipart.addFormDataPart(key, mParams[key].toString())
            }
        }
        //文件
        if (mHttpFileList.size > 0) {
            try {
                for (httpfile in mHttpFileList) {
                    if (httpfile == null || httpfile.file == null
                            || httpfile.file!!.exists() === false || httpfile.file!!.isDirectory) {
                        continue
                    }
                    val filePath = httpfile.file!!.absolutePath
                    val fileSimpleName = getFileSimpleName(filePath)
                    val fileNameMap = URLConnection.getFileNameMap()
                    var mimeType: String? = fileNameMap.getContentTypeFor(filePath)
                    if (mimeType == null) {
                        mimeType = "application/octet-stream"
                    }
                    LogUtil.makeLog("HttpUploadRequest", "====file mimetype:$mimeType")
                    val mediaType = MediaType.parse(mimeType)
                    multipart.addFormDataPart(if (httpfile.param == null) "" else httpfile.param,
                            fileSimpleName ?: "file", RequestBody.create(mediaType, httpfile.file))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        val builder = Request.Builder()
        if (mHeaders != null) {
            for (key in mHeaders.keys) {
                builder.addHeader(key, mHeaders[key])
            }
        }
        builder.url(mUrl).post(multipart.build())
        return builder.build()
    }

    override fun getParser(): ResponseParser<*> {
        if (mParser == null) {
            mParser = UploadResponseParser(this)
        }
        return mParser
    }

    /**
     * 需要上传得文件
     */
    fun file(httpfile: HttpFile): HttpUploadRequest<*> {
        mHttpFileList.clear()
        mHttpFileList.add(httpfile)
        return this
    }

    /**
     * 需要上传得文件列表
     */
    fun files(fileList: List<HttpFile>?): HttpUploadRequest<*> {
        if (fileList != null) {
            mHttpFileList.clear()
            mHttpFileList.addAll(fileList)
        }
        return this
    }


    private fun getFileSimpleName(path: String?): String? {
        if (path == null) {
            return null
        }
        val lastsplash = path.lastIndexOf(File.separator)
        return if (lastsplash == -1) {
            path
        } else path.substring(lastsplash + 1)
    }
}