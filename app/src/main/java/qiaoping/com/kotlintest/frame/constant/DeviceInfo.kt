package qiaoping.com.kotlintest.frame.constant

import android.os.Build

/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:
 */


class DeviceInfo {
    companion object {
        var WIDTHPIXELS = 800
        var HEIGHTPIXELS = 640
        var DENSITYDPI = 240
        var DENSITY = 1.5f
        var DEVICE_MODEL: String
        var SYSTEM_SDK_VERSION: String

        init {
            DEVICE_MODEL = Build.MODEL
            SYSTEM_SDK_VERSION = Build.VERSION.SDK_INT.toString()
        }
    }
}