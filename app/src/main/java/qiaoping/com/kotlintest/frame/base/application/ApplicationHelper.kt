package qiaoping.com.kotlintest.frame.base.application

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log

/**
 * Author: qiaoping.xiao  on 2018/3/13.
 * description:
 */
 class ApplicationHelper {
    val TAG: String = javaClass.simpleName

    companion object {
         fun getApplication(): Application? {
            try {
                val clazz = Application::class
                val list = clazz.members
                for (kCallable in list) {
                    when (kCallable.name) {
                        "currentApplication" -> return kCallable.call() as Application?
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "获取application实例失败啦，哎呀！~哎呀呀！~~~${e.message}")
            }
            return null
        }
    }
}