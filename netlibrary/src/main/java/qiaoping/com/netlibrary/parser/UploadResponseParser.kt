package qiaoping.com.netlibrary.parser

import qiaoping.com.netlibrary.request.HttpRequest

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class UploadResponseParser<T>(request: HttpRequest<T>) : JsonResponseParser<T>(request) {
}