package qiaoping.com.netlibrary.cookies

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import qiaoping.com.common.KotlinApplication
import qiaoping.com.common.utils.SharePreferenceUtil
import java.util.HashSet

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val builder = chain!!.request().newBuilder()
        val preferences = SharePreferenceUtil.getStringSet(KotlinApplication.getContext()) as HashSet<*>
        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie.toString())
            Log.v("OkHttp", "Adding Header: $cookie") // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build())
    }
}