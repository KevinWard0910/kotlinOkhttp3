package qiaoping.com.netlibrary.http

import android.content.Context
import qiaoping.com.netlibrary.cookies.CookieJarManager
import qiaoping.com.netlibrary.cookies.PersistentCookieStore
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
class HttpConfig {
    companion object {
    /**
     * 默认读取超时时间--30s
     */
    private val DEFAULT_READTIMEOUT:Int = 30
    /**
     * 默认写超时时间--15s
     */
    private val DEFAULT_WRITETIMEOUT:Int = 15
    /**
     * 默认连接超时时间--15s
     */
    private val DEFAULT_CONNECTTIMEOUT:Int = 15
    /**
     * 数据读取超时时间，默认为30s
     */
    var readTimeOut:Int = DEFAULT_READTIMEOUT
    /**
     * 写超时时间，默认为15s
     */
    var writeTimeOut:Int = DEFAULT_WRITETIMEOUT
    /**
     * 连接超时时间，默认为15s
     */
    var connectTimeOut:Int = DEFAULT_CONNECTTIMEOUT
    }

    /**
     * 是否支持cookie
     */
    var supportCookie: Boolean = false

    /**
     * cookie管理策略
     */
    var cookieHandler: CookieManager? = null
    /**
     * 缓存默认大小
     */
    var cacheSize = (10 * 1024 * 1024).toLong()
    /**
     * 缓存目录，默认为系统给应用分配的缓存目录
     */
    var cacheDirectory: String
    /**
     * 是否设置缓存大小
     */
    var isSetCacheSize: Boolean = false
    /**
     *
     */
    var cookieJarManager: CookieJarManager

    constructor(context: Context) {
        cookieHandler = CookieManager(PersistentCookieStore(context),
        CookiePolicy.ACCEPT_ORIGINAL_SERVER)
        cookieJarManager = CookieJarManager(context)
        cacheDirectory = context.cacheDir.absolutePath
    }

}