package qiaoping.com.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
open class KotlinApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }
    }


}