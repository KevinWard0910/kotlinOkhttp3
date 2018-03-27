package qiaoping.com.kotlintest.frame.base

/**
 * Author: qiaoping.xiao  on 2018/3/13.
 * description:
 */
open class BaseConstant {
    var isTest:Boolean = false
    open val PACKAGE_NAME = javaClass.`package`.name
    open var APP_VERSION_CODE =1
    open var APP_VERSION_NAME = "1.0"

}