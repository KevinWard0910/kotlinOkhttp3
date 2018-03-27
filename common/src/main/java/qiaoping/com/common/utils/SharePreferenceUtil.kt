package qiaoping.com.common.utils

import android.content.Context
import java.util.HashSet

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class SharePreferenceUtil {
    private constructor() {
        throw AssertionError()
    }

    companion object {
        /** 文件名  */
        var PREFERENCE_NAME = "HX_BUSINESS_APP"
        /** 第一次启动app标识  */
        var FIRST_APP = "FIRST_APP"
        /** 阿里方式生成的唯一设备标识  */
        var DEVICE_ALI_UTDID = "ALI_UTDID"
        /**用户登录token */
        var USER_TOKEN = "usertoken"
        /**用户登录信息 */
        var USER_INFO_PROFILE = "USER_INFO_PROFILE"
        /**用户登录信息 */
        var USER_INFO = "USER_INFO"
        /**相机拍照文件 */
        var CAPTURE_PHOTO_PATH = "CAPTURE_PHOTO_PATH"
        /**用户资料 */
        var USER_PROFILE = "USER_PROFILE"
        /**即时通讯用户资料 */
        var LOGIN_IM = "LOGIN_IM"
        /**定位信息 */
        var LOCATION = "LOCATION"
        /**APPICON信息 */
        var APPICON = "APPICON"

        /**
         * put string preferences
         *
         * @param context
         * @param key The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putString(context: Context, key: String, value: String): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        /**
         * get string preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
         * name that is not a string
         * @see .getString
         */
        fun getString(context: Context, key: String): String? {
            return getString(context, key, null)
        }

        /**
         * get string preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a string
         */
        fun getString(context: Context, key: String, defaultValue: String?): String? {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getString(key, defaultValue)
        }

        /**
         * put int preferences
         *
         * @param context
         * @param key The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putInt(context: Context, key: String, value: Int): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(key, value)
            return editor.commit()
        }

        /**
         * get int preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a int
         * @see .getInt
         */
        fun getInt(context: Context, key: String): Int {
            return getInt(context, key, -1)
        }

        /**
         * get int preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a int
         */
        fun getInt(context: Context, key: String, defaultValue: Int): Int {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getInt(key, defaultValue)
        }

        /**
         * put long preferences
         *
         * @param context
         * @param key The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putLong(context: Context, key: String, value: Long): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putLong(key, value)
            return editor.commit()
        }

        /**
         * get long preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a long
         * @see .getLong
         */
        fun getLong(context: Context, key: String): Long {
            return getLong(context, key, -1)
        }

        /**
         * get long preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a long
         */
        fun getLong(context: Context, key: String, defaultValue: Long): Long {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getLong(key, defaultValue)
        }

        /**
         * put float preferences
         *
         * @param context
         * @param key The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putFloat(context: Context, key: String, value: Float): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putFloat(key, value)
            return editor.commit()
        }

        /**
         * get float preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
         * name that is not a float
         * @see .getFloat
         */
        fun getFloat(context: Context, key: String): Float {
            return getFloat(context, key, -1f)
        }

        /**
         * get float preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a float
         */
        fun getFloat(context: Context, key: String, defaultValue: Float): Float {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getFloat(key, defaultValue)
        }

        /**
         * put boolean preferences
         *
         * @param context
         * @param key The name of the preference to modify
         * @param value The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putBoolean(context: Context, key: String, value: Boolean): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }

        /**
         * get boolean preferences, default is false
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
         * name that is not a boolean
         * @see .getBoolean
         */
        fun getBoolean(context: Context, key: String): Boolean {
            return getBoolean(context, key, false)
        }

        /**
         * get boolean preferences
         *
         * @param context
         * @param key The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a boolean
         */
        fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getBoolean(key, defaultValue)
        }

        /**
         * 保存纬度
         */
        fun putLatitude(context: Context, key: String, value: String): Boolean {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        /**
         * 保存经度
         */
        fun putLongitude(context: Context, key: String, value: String): Boolean {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        /**获取纬度 */
        fun getLatitude(context: Context, key: String, value: String): String? {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return sp.getString(key, value)
        }

        /**获取经度 */
        fun getLongitude(context: Context, key: String, value: String): String? {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return sp.getString(key, value)
        }

        /**
         * 保存服务热线
         */
        fun putHotline(context: Context, key: String, value: String): Boolean {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        /**获取服务热线 */
        fun getHotline(context: Context, key: String, value: String): String? {
            val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return sp.getString(key, value)
        }


        val COOKIES = "COOKIES"
        fun putStringSet(context: Context, cookies: HashSet<String>): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putStringSet(COOKIES, cookies)
            return editor.commit()
        }

        fun getStringSet(context: Context): Set<String> {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getStringSet(COOKIES, HashSet())
        }

    }
}