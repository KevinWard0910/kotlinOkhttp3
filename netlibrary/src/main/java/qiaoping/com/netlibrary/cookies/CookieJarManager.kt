package qiaoping.com.netlibrary.cookies

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class CookieJarManager : CookieJar {
    private var cookieStore: CookieJarStore

    constructor(context: Context) {
        cookieStore = CookieJarStore(context)
    }

    override fun saveFromResponse(url: HttpUrl?, cookies: MutableList<Cookie>?) {
        if (cookies != null && cookies.size > 0){
            cookies.forEach { cookies.add(it) }
        }
    }
    override fun loadForRequest(url: HttpUrl?): MutableList<Cookie> {
        val cookies = cookieStore.getCookies()
        return cookies as MutableList<Cookie>
    }

    fun clearCookies(){
        cookieStore.removeAll()
    }

}