package qiaoping.com.netlibrary.assist

import android.content.Intent
import android.os.Looper
import android.os.Handler
import qiaoping.com.netlibrary.request.HttpRequest

/**
 * Author: qiaoping.xiao  on 2018/3/16.
 * description:
 */
class HttpHandler {

    lateinit var mDeliveryhandler: Handler

    constructor() {
        mDeliveryhandler = Handler(Looper.getMainLooper())
    }


    companion object {
        private var delivery: HttpHandler? = null
        @Synchronized
        fun get(): HttpHandler {
            if (delivery == null) {
                delivery = HttpHandler()
            }
            return delivery!!
        }
    }

    fun reLogin(request: HttpRequest<*>){
        //TODO 重登操作
//        val intent = Intent()
//        intent.setClass(request.mContext,)
//        request.getContext().startActivity()
    }

    /**
     * 请求开始
     *
     * @param callback
     */
    fun  onStart(callback: UserCallBack?) {
        mDeliveryhandler.post({
            callback?.start()
        })
    }

    /**
     * 请求成功
     *
     * @param result
     * @param callback
     */
    fun  onSuccess(result: ResponseData, callback: UserCallBack?) {
        mDeliveryhandler.post({
            callback?.onSuccess(result)
        })
    }

    /**
     * 请求失败
     *
     * @param callback
     */
    fun onFailed(result: ResponseData, callback: UserCallBack?) {
        mDeliveryhandler.post({
            callback?.onFailure(result)
        })
    }

    /**
     * 上传或下载中,进度回调
     *
     * @param callback
     */
    fun  onProcess(totalCount: Long, currCount: Long, callback: UserCallBack?) {
        mDeliveryhandler.post({
            callback?.onLoading(totalCount, currCount)
        })
    }
}