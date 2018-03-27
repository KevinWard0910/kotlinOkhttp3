package qiaoping.com.netlibrary.parser

import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.request.HttpRequest

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
open class JsonResponseParser<T> : ResponseParser<T> {
    override fun parser(response: Response): ResponseData {
        val data = ResponseData()
        try {
            data.status = true
            if (response == null) {
                return data
            }

            val content = response.body()!!.string()
            if (content == null) {
                data.message = "内容为空"
                return data
            }

            LogUtil.makeLog("JsonResponseParser", "====json:$content")
            val clazz = request.clazz

            if (request.returnRawData || clazz == null) {
                data.data = content
                data.message = "请求成功"
                return data
            } else {
                val jo = JSONObject(content)
                if (jo.has("code")) {
                    val isOk = jo.getInt("code")
                    val strOk = jo.getString("code")
                    when {
                        isOk != null -> {
                            data.status = true
                            data.code = isOk.toString()
                        }
                        strOk != null -> {
                            data.status = true
                            data.code = strOk
                        }
                        else -> data.status = false
                    }
                } else if (jo.has("errorCode")) {
                    val isOk = jo.getInt("errorCode")
                    data.status = isOk == 0 || isOk == 200
                }


                if (!jo.isNull("message")) {
                    data.message = jo.getString("message")
                } else if (!jo.isNull("errorMsg")) {
                    data.message = jo.getString("errorMsg")
                }

                if (jo.isNull("dataMap")) {
                    return data
                }

                if (!jo.isNull("hasNextPage")) {
                    data.hasNextPage = jo.getBoolean("hasNextPage")
                }


                val dataString = jo.getString("dataMap")
                if (dataString.substring(0, 1).indexOf("[") != -1) {
                    data.data = mGson.fromJson(dataString,clazz)
//                    data.data = JsonUtil.parseList(dataString, clazz)

                } else {
                    val jsonData = JSONObject(dataString)
                    val dataContent: String?
                    dataContent = jsonData.toString()

                    if (dataContent == null) {
                        return data
                    }
                    val bracket = dataContent.indexOf("[")
                    if (bracket != -1 && dataContent.substring(0, bracket + 1).trim { it <= ' ' }.length == 1) {
                        data.data = mGson.fromJson(dataContent,clazz)
//                        data.data = JsonUtil.parseList(dataContent, clazz)
                    } else {
                        data.data = mGson.fromJson(dataContent,clazz)
//                        data.data = JsonUtil.parse(dataContent, clazz)
                    }
                }


                return data
            }
        } catch (e: Exception) {
            data.status = false
            data.message = "解析错误"
        }

        return data

    }

    constructor(request: HttpRequest<T>) : super(request)
}