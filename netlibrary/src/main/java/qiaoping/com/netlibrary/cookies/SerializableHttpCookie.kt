package qiaoping.com.netlibrary.cookies

import android.content.Context
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.net.HttpCookie

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class SerializableHttpCookie : Serializable {
    @Transient
    private var cookie: HttpCookie
    @Transient
    private var clientCookie: HttpCookie? = null

    constructor(cookie: HttpCookie) {
        this.cookie = cookie
    }

    fun getCookie(): HttpCookie {
        var bestCookie = cookie
        if (clientCookie != null) {
            bestCookie = clientCookie!!
        }
        return bestCookie
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie.name)
        out.writeObject(cookie.value)
        out.writeObject(cookie.comment)
        out.writeObject(cookie.commentURL)
        out.writeObject(cookie.domain)
        out.writeLong(cookie.maxAge)
        out.writeObject(cookie.path)
        out.writeObject(cookie.portlist)
        out.writeInt(cookie.version)
        out.writeBoolean(cookie.secure)
        out.writeBoolean(cookie.discard)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val name = `in`.readObject() as String
        val value = `in`.readObject() as String
        clientCookie = HttpCookie(name, value)
        clientCookie!!.comment = `in`.readObject() as String
        clientCookie!!.commentURL = `in`.readObject() as String
        clientCookie!!.domain = `in`.readObject() as String
        clientCookie!!.maxAge = `in`.readLong()
        clientCookie!!.path = `in`.readObject() as String
        clientCookie!!.portlist = `in`.readObject() as String
        clientCookie!!.version = `in`.readInt()
        clientCookie!!.secure = `in`.readBoolean()
        clientCookie!!.discard = `in`.readBoolean()
    }
}