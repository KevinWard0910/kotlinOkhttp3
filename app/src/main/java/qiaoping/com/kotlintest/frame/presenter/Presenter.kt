package qiaoping.com.kotlintest.frame.presenter

import android.content.Context

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:数据提供者基类
 */
abstract class Presenter<T> {

    val mContext: Context
    var mParams: HashMap<String, Any>? = null
    val mvpView: T

    constructor(context: Context, mvpView: T) {
        this.mvpView = mvpView
        mContext = context
    }

    fun getParams():HashMap<String,Any>{
        if (mParams ==null){
            mParams = HashMap()
        }
        return mParams!!
    }

    fun put(key:String,value:Any){
        if (mParams ==null){
            mParams = HashMap()
        }
        mParams!![key] = value
    }

}