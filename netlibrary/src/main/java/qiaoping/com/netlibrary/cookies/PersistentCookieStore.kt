package qiaoping.com.netlibrary.cookies

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import java.io.*
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.experimental.and

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:cook持久层
 */
open class PersistentCookieStore : CookieStore {


    companion object {
        private val LOG_TAG: String = "PersistentCookieStore";
        private val COOKIE_PREFS: String = "CookiePrefsFile"
        private val COOKIE_NAME_PREFIX: String = "cookie_"
    }


    private val cookies: HashMap<String, ConcurrentHashMap<String, HttpCookie>>
    private val cookiePrefs: SharedPreferences

    constructor(context: Context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0)
        cookies = java.util.HashMap()

        // Load any previously stored cookies into the store
        val prefsMap = cookiePrefs.getAll()
        for (entry in prefsMap.entries) {
            if (entry.value as String != null && !(entry.value as String).startsWith(COOKIE_NAME_PREFIX)) {
                val cookieNames = TextUtils.split(entry.value as String, ",")
                for (name in cookieNames) {
                    val encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null)
                    if (encodedCookie != null) {
                        val decodedCookie = decodeCookie(encodedCookie)
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(entry.key))
                                cookies[entry.key] = ConcurrentHashMap()
                            cookies[entry.key]!![name] = decodedCookie!!
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun removeAll(): Boolean {
        val prefsWriter = cookiePrefs.edit()
        prefsWriter.clear()
        prefsWriter.commit()
        cookies.clear()
        return true
    }

    @SuppressLint("ApplySharedPref")
    override fun add(uri: URI?, cookie: HttpCookie?) {
        // Save cookie into local store, or remove if expired
        if (!cookie!!.hasExpired()) {
            if (!cookies.containsKey(cookie.domain))
                cookies[cookie.domain] = ConcurrentHashMap()
            cookies[cookie.domain]!![cookie.name] = cookie
        } else {
            if (cookies.containsKey(cookie.domain))
                cookies[cookie.domain]!!.remove(cookie.domain)
        }

        // Save cookie into persistent store
        val prefsWriter = cookiePrefs.edit()
        prefsWriter.putString(cookie.domain, TextUtils.join(",", cookies[cookie.domain]!!.keys))
        prefsWriter.putString(COOKIE_NAME_PREFIX + cookie.name, encodeCookie(SerializableHttpCookie(cookie)))
        prefsWriter.commit()
    }

    protected fun getCookieToken(uri: URI?, cookie: HttpCookie?): String {
        return cookie!!.name + cookie.domain
    }

    override fun getCookies(): MutableList<HttpCookie> {
        val ret = ArrayList<HttpCookie>()
        for (key in cookies.keys)
            ret.addAll(cookies[key]!!.values)

        return ret
    }

    override fun getURIs(): MutableList<URI> {
        val ret = ArrayList<URI>()
        for (key in cookies.keys)
            try {
                ret.add(URI(key))
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        return ret
    }

    @SuppressLint("ApplySharedPref")
    override fun remove(uri: URI?, cookie: HttpCookie?): Boolean {
        val name = getCookieToken(uri, cookie)

        if (cookies.containsKey(uri!!.host) && cookies[uri.host]!!.containsKey(name)) {
            cookies[uri.host]!!.remove(name)

            val prefsWriter = cookiePrefs.edit()
            if (cookiePrefs.contains(COOKIE_NAME_PREFIX + name)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + name)
            }
            prefsWriter.putString(uri.host, TextUtils.join(",", cookies[uri.host]!!.keys))
            prefsWriter.commit()
            return true
        } else {
            return false
        }
    }

    override fun get(uri: URI?): MutableList<HttpCookie> {
        val ret = ArrayList<HttpCookie>()
        for (key in cookies.keys) {
            if (uri!!.host.contains(key)) {
                ret.addAll(cookies[key]!!.values)
            }
        }
        return ret
    }

    protected fun encodeCookie(cookie: SerializableHttpCookie?): String? {
        if (cookie == null)
            return null
        val os = ByteArrayOutputStream()
        try {
            val outputStream = ObjectOutputStream(os)
            outputStream.writeObject(cookie)
        } catch (e: IOException) {
            return null
        }

        return byteArrayToHexString(os.toByteArray())
    }

    protected fun decodeCookie(cookieString: String): HttpCookie? {
        val bytes = hexStringToByteArray(cookieString)
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        var cookie: HttpCookie? = null
        try {
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as SerializableHttpCookie).getCookie()
        } catch (e: IOException) {
        } catch (e: ClassNotFoundException) {
        }

        return cookie
    }

    /**
     * Using some super basic byte array <-> hex conversions so we don't have to rely on any
     * large Base64 libraries. Can be overridden if you like!
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected fun byteArrayToHexString(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (element in bytes) {
            val v = element and 0xff.toByte()
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v.toInt()))
        }
        return sb.toString().toUpperCase(Locale.US)
    }

    /**
     * Converts hex values from strings to byte arra
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

}