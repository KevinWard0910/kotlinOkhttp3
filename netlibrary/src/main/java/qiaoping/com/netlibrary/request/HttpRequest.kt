package qiaoping.com.netlibrary.request

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Request
import qiaoping.com.common.block.LoginBlock
import qiaoping.com.common.utils.LogUtil
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.assist.UserCallBack
import qiaoping.com.netlibrary.http.HttpClient
import qiaoping.com.netlibrary.parser.ResponseParser

/**
 * Author: qiaoping.xiao  on 2018/3/15.
 * description:Http网络请求基类
 */
abstract class HttpRequest<T> {

    protected val TAG = javaClass.simpleName
    var mContext: Context
    /**请求url*/
    protected lateinit var mUrl: String
    /**请求Method*/
    protected var mMethod: Int=0
    /**请求参数*/
    protected lateinit var mParams: HashMap<String, Any>
    /**tag*/
    protected lateinit var mTag: String
    /**header*/
    protected lateinit var mHeaders: HashMap<String, String>
    /**返回数据对应实体类型*/
    lateinit var clazz: Class<*>
    /**是否加密*/
    var secret: Boolean = false
    /**请求的数据是否为列表*/
    protected var isListData: Boolean = false
    /**是否显示处理框*/
    protected var showDialog: Boolean = true
    /**是否需要附加默认参数*/
    protected var appendDefaultParam: Boolean = true
    /**是否需要附加Heander*/
    protected var appendDefaultHeader: Boolean = true
    /**是否返回原始数据*/
    var returnRawData: Boolean = false
    /**缓存策略*/
    protected var cacheStrategy: CacheStrategy = CacheStrategy.DEFAULT
    /**是否要登录拦截*/
    var isLoginIntercept: Boolean = true
    /**Okhttp call*/
    private lateinit var mCall: Call

    var mUserCallBack: UserCallBack? = null

    constructor(context: Context?) {
        mContext = context!!
    }

    /**返回Okhttp Request */
    abstract fun build(): Request?

    /**
     * 获得对应的解析类
     */
    abstract fun getParser(): ResponseParser<*>

    /**
     * url
     */
    fun url(url: String): HttpRequest<*> {
        this.mUrl = url
        return this
    }

    /**
     * 请求方式
     */
    fun method(method: Int): HttpRequest<*> {
        this.mMethod = method
        return this
    }

    /**
     * 设置请求方式为get
     */
    fun get(): HttpRequest<*> {
        this.mMethod = Method.METHOD_GET
        return this
    }

    /**
     * 设置请求方式为post
     */
    fun post(): HttpRequest<*> {
        this.mMethod = Method.METHOD_POST
        return this
    }

    /**
     * params
     */
    fun params(params: Map<String, Any>): HttpRequest<*> {
        mParams.putAll(params)
        return this
    }

    /**
     * 标志请求
     */
    fun tag(tag: String): HttpRequest<*> {
        this.mTag = tag
        return this
    }

    /**
     * headers
     */
    fun headers(headers: Map<String, String>): HttpRequest<*> {
        this.mHeaders = headers as HashMap<String, String>
        return this
    }

    fun getHeaders(): Map<String, String> {
        return mHeaders
    }

    /**
     * 返回数据对应的class
     */
    fun clazz(clazz: Class<*>): HttpRequest<T> {
        this.clazz = clazz
        return this
    }

    /**
     * 回调
     */
    fun callback(callback: UserCallBack): HttpRequest<*> {
        this.mUserCallBack = callback
        return this
    }

    fun getUserCallBack(): UserCallBack? {
        return this.mUserCallBack
    }

    /**
     * 是否签名加密
     */
    fun secret(value: Boolean): HttpRequest<*> {
        this.secret = value
        return this
    }

    fun setLoginIntercept(isLoginIntercept: Boolean): HttpRequest<*> {
        this.isLoginIntercept = isLoginIntercept
        return this
    }

    fun getLoginIntercept(): Boolean {
        return isLoginIntercept
    }

    /**
     * 显示等待提示框
     */
    fun dialog(show: Boolean): HttpRequest<*> {
        this.showDialog = show
        return this
    }

    /**
     * 是否附加默认参数
     */
    fun defaultParam(defaultParam: Boolean): HttpRequest<*> {
        this.appendDefaultParam = defaultParam
        return this
    }

    /**
     * 是否附加默认header
     */
    fun defaultHeader(defaultHeader: Boolean): HttpRequest<*> {
        this.appendDefaultHeader = defaultHeader
        return this
    }

    /**
     * 返回原始数据
     */
    fun rawData(rawData: Boolean): HttpRequest<*> {
        this.returnRawData = rawData
        return this
    }

    fun cacheStrategy(cacheStrategy: CacheStrategy): HttpRequest<*> {
        this.cacheStrategy = cacheStrategy
        return this
    }

    /**
     * 异步提交
     */
    fun commitAsync() {
        HttpClient.instance()!!.commitAsync(this)
    }

    /**
     * 同步提交
     */
    fun commitSync(): ResponseData {
        return HttpClient.instance()!!.commitSync(this)
    }

    /**
     * 根据请求获取当前请求的缓存控制器
     * @return
     */
    protected fun getCacheControl(): CacheControl {
        var cacheControl: CacheControl? = null
        when (cacheStrategy) {
            HttpRequest.CacheStrategy.FORCE_NETWORK -> cacheControl = CacheControl.FORCE_NETWORK
            HttpRequest.CacheStrategy.FORCE_CACHE -> cacheControl = CacheControl.FORCE_CACHE
            else -> cacheControl = CacheControl.Builder().build()
        }
        return cacheControl
    }

    /**
     * 附加默认参数
     */
    protected fun appendDefaultParams() {
        if (mParams == null) {
            mParams = HashMap()
        }
        //TODO
        //根据具体项目决定附带那些默认参数
        //TODO
        //        UserInfoBean user = LoginBlock.getUserInfo();
        //        if(user != null){
        //            mParams.put("access_token",user.accessToken);
        //        }
        //mParams.put("mt_token", ApiTokenUtil.getEncryptedString(ApiTokenUtil.getTokenTime()));

    }

    protected fun appendDefaultHeaders() {
        if (mHeaders == null) {
            mHeaders = HashMap()
        }
        val authToken = LoginBlock.getAuthToken()
        if (authToken != null) {
            mHeaders["x-auth-token"] = authToken
            LogUtil.makeLog("token", authToken)
        }
    }

    fun setCall(call: Call) {
        this.mCall = call
    }

    /**取消请求 */
    fun cancelRequest() {
        if (!mCall.isCanceled) {
            mCall.cancel()
        }
    }

    class Method {
        companion object {
            val METHOD_GET = 1
            val METHOD_POST = 2
            val METHOD_PUT = 3
            val METHOD_DELETE = 4
            val METHOD_MAX = 5
        }
    }

    enum class CacheStrategy {
        /**默认策略，请求结果会缓存起来，下次请求如果存在缓存在从缓存中取结果，没有则请求服务*/
        DEFAULT,
        /**无缓存，强制使用网络请求数据*/
        FORCE_NETWORK,
        /**强制使用缓存，如果存在缓存则从缓存中取数据，如果不存在该请求的缓存，则请求失败(504 Unsatisfiable Request)*/
        FORCE_CACHE
    }
}