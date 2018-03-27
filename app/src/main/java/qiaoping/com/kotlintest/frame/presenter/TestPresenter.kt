package qiaoping.com.kotlintest.frame.presenter

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject
import qiaoping.com.kotlintest.frame.bean.UserInfoBean
import qiaoping.com.kotlintest.frame.presenter.view.TestView
import qiaoping.com.netlibrary.assist.ResponseData
import qiaoping.com.netlibrary.assist.UserCallBack
import qiaoping.com.netlibrary.request.HttpJsonRequest
import qiaoping.com.netlibrary.request.HttpRequest
import org.json.JSONArray



/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:测试类
 */
class TestPresenter(context: Context, mvpView: TestView) : Presenter<TestView>(context, mvpView) {

    private var request: HttpRequest<TestView> = HttpJsonRequest(context)
    private val mGson:Gson= Gson()

    fun login(params1:String,params2:String){
        put("params1",params1)
        put("params2",params2)

        request.params(mParams!!).post().url("url").clazz(UserInfoBean::class.java).callback(object :UserCallBack{

            override fun onSuccess(result: ResponseData) {
                val jsonObject = JSONObject(result.data.toString())
                val code = jsonObject.getInt("code")
                val message = jsonObject.getString("message")
                val data = jsonObject.getJSONObject("dataMap").getString("data")
//                JSONArray.toList(JSONArray.fromObject(arrayStr), UserInfoBean::class.java) as List<UserInfoBean>
//                val list = mGson.fromJson(data, List<UserInfoBean>::class.java)
//                mvpView.onSuccess(list)
            }

            override fun onLoading(count: Long, current: Long) {
            }

            override fun onFailure(result: ResponseData) {
            }

        }
        ).commitAsync()
    }
}