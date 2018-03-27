package qiaoping.com.kotlintest.frame.constant

import qiaoping.com.kotlintest.frame.base.BaseConstant
import qiaoping.com.kotlintest.frame.utils.getDataPath


/**
 * Author: qiaoping.xiao  on 2018/3/13.
 * description:全局常量
 */
var CACHE_ROOT: String = ""
var CACHE_DATA: String = ""
var CACHE_IMG: String = ""
var CACHE_AUDIO: String = ""
var CACHE_CHAT: String = ""
var CACHE_ERROR: String = ""

class GlobalConstants : BaseConstant() {

    init {
        APP_VERSION_CODE = 1
        APP_VERSION_NAME = "1.0"
        CACHE_ROOT = getDataPath() + "cache/"
        CACHE_DATA = CACHE_ROOT + "data/"
        CACHE_IMG = CACHE_ROOT + "images/"
        CACHE_AUDIO = CACHE_ROOT + "audio/"
        CACHE_CHAT = CACHE_ROOT + "chat/"
        CACHE_ERROR = CACHE_ROOT + "error/"
    }

    enum class ServerType{
        RELEASE,
        STG,
        TEST,
        UAT1,
        DEV
    }
}
