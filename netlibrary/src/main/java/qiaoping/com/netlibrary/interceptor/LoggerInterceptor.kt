package qiaoping.com.netlibrary.interceptor

import okhttp3.*
import okio.Buffer
import qiaoping.com.common.utils.LogUtil
import java.io.IOException

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
class LoggerInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val response = chain.proceed(request)
        return logForResponse(response)
    }

    private fun logForResponse(response: Response): Response {
        try {
            //===>response log
            val builder = response.newBuilder()
            val clone = builder.build()
            val url = clone.request().url()
            val code = clone.code()
            val protocol = clone.protocol()
            val message = clone.message()
            var body = clone.body()
            var contentType = ""
            var content = ""
            if (body != null) {
                val mediaType = body.contentType()
                if (mediaType != null) {
                    contentType = mediaType.toString()
                    if (isText(mediaType)) {
                        val resp = body.string()
                        content = resp
                        body = ResponseBody.create(mediaType, resp)
                        LogUtil.d("url---->%s \n " +
                                "code---->%d \n" +
                                "protocol---->%s \n" +
                                "message---->%s \n" +
                                "contentType---->%s \n" +
                                "content---->%s \n", url.toString(), code, protocol.toString(), message, contentType, content)
                        return response.newBuilder().body(body).build()
                    } else {
                        content = " maybe [file part] , too large too print , ignored!"
                    }
                }
            }

            LogUtil.d("url---->%s \n " +
                    "code---->%d \n" +
                    "protocol---->%s \n" +
                    "message---->%s \n" +
                    "contentType---->%s \n" +
                    "content---->%s \n", url.toString(), code, protocol.toString(), message, contentType, content)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type() == "text") {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype() == "json" ||
                    mediaType.subtype() == "xml" ||
                    mediaType.subtype() == "html" ||
                    mediaType.subtype() == "webviewhtml")
                return true
        }
        return false
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }

    }
}