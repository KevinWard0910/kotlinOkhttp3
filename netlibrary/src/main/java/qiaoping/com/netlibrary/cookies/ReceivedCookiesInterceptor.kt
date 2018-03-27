package qiaoping.com.netlibrary.cookies

import okhttp3.Interceptor
import okhttp3.Response
import qiaoping.com.common.KotlinApplication
import qiaoping.com.common.utils.SharePreferenceUtil

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class ReceivedCookiesInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val originalResponse = chain!!.proceed(chain.request())
        if (!originalResponse.headers("Set_Cookies").isEmpty()) run {
            val cookies: HashSet<String> = HashSet()
            val headers = originalResponse.headers("Set_Cookies")
            for (header in headers) {
                cookies.add(header)
            }

            SharePreferenceUtil.putStringSet(KotlinApplication.getContext(),cookies)
        }
        return originalResponse
    }
}