package qiaoping.com.common.utils

import android.content.Context
import android.util.Log
import com.orhanobut.logger.Logger
import qiaoping.com.common.BuildConfig

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
class LogUtil {
    companion object {
        /**
         * LOG显示
         * @param tag
         * @param msg
         */
        fun makeLog(tag: String, msg: String) {
            if (BuildConfig.LOG_DEBUG) {
                Log.i(tag, msg)
            }
        }

        /**
         * LOG显示
         * @param tag
         * @param msg
         */
        fun makeLog(context: Context, tag: String?, msg: String?) {
            if (tag == null || msg == null) {
                return
            }
            if (BuildConfig.LOG_DEBUG) {
                try {
                    Log.i(context.javaClass.simpleName + "_" + tag, msg)
                } catch (e: Exception) {
                    Log.i("SimpleName Not Found$tag", msg)
                }

            }

        }


        fun i(msg: String, vararg args: Any) {
            if (BuildConfig.LOG_DEBUG)
                Logger.i(msg, args)
        }

        fun e(msg: String, vararg args: Any) {
            if (BuildConfig.LOG_DEBUG)
                Logger.e(msg, args)
        }

        fun d(msg: String, vararg args: Any) {
            if (BuildConfig.LOG_DEBUG)
                Logger.d(msg, args)
        }

        fun v(msg: String, vararg args: Any) {
            if (BuildConfig.LOG_DEBUG)
                Logger.v(msg, args)
        }

        fun w(msg: String, vararg args: Any) {
            if (BuildConfig.LOG_DEBUG)
                Logger.w(msg, args)
        }

        fun json(json: String) {
            if (BuildConfig.LOG_DEBUG)
                Logger.json(json)
        }

        fun xml(xml: String) {
            if (BuildConfig.LOG_DEBUG)
                Logger.xml(xml)
        }
    }
}