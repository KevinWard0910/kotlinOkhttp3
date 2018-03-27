package qiaoping.com.netlibrary.parser

import android.content.Context
import okhttp3.Response
import qiaoping.com.netlibrary.assist.HttpHandler
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.request.HttpRequest
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class DownloadResponseParser<T>:ResponseParser<T> {

    private val downloadFilePath:String

   constructor(request:HttpRequest<T>,filepath:String):super(request){
        downloadFilePath=filepath
   }


    override fun parser(response: Response): ResponseData {
        val data = ResponseData()
        try {
            data.status = true
            if (response == null) {
                return data
            }
            val source = response.body()!!.source()
            var totalCount = response.body()!!.contentLength()
            val buf = ByteArray(2048)
            var len = 0
            var ins: InputStream? = null
            var fos: FileOutputStream? = null
            try {
                ins = source.inputStream()

                var hasRead: Long = 0
                if (totalCount == (-1).toLong()) {
                    totalCount = ins!!.available().toLong()
                }

                if (totalCount == 0L) {
                    data.message = "文件内容为空"
                    data.code = "200"
                    return data
                }

                HttpHandler.get().onProcess(totalCount, hasRead, request.getUserCallBack())

                makeParentDir(downloadFilePath)
                val file = File(downloadFilePath)
                if (file.isDirectory) {
                    data.status = false
                    data.message = "保存路径错误"
                    return data
                }
                if (file.exists()) {
                    file.delete()
                }
                fos = FileOutputStream(file)
                while (ins.read().also { len=it } != -1) {
                    fos.write(buf, 0, len)
                    hasRead += len.toLong()
                    HttpHandler.get().onProcess(totalCount, hasRead, request.getUserCallBack())
                }
                fos.flush()
                data.message = "下载成功"
                data.code = "200"
                return data
            } catch (e: Exception) {
                data.status = false
                data.message = "下载失败"
            } finally {
                try {
                    if (fos != null) {
                        fos.close()
                    }
                    if (ins != null) {
                        ins.close()
                    }
                } catch (e: IOException) {
                }

            }

        } catch (e: Exception) {
            data.status = false
            data.message = "下载错误"
        }

        return data

    }

    /**
     * 判断其父目录是否存在，不存在则创建
     * Author: hyl
     * Time: 2015-8-21下午11:13:35
     * @param path
     */
    private fun makeParentDir(path: String) {
        val parentPath = getParentPath(path)
        val file = File(parentPath)
        if (!file.exists()) {
            makeParentDir(parentPath)
            file.mkdir()
        }
    }

    /**
     * 获取父级目录
     * Author: hyl
     * Time: 2015-8-21下午11:12:44
     * @param path
     * @return
     */
    private fun getParentPath(path: String): String {
        var path = path
        if (path == "/") {
            return path
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length - 1)
        }
        path = path.substring(0, path.lastIndexOf("/"))
        return if (path == "") "/" else path
    }

}