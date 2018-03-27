package qiaoping.com.kotlintest.frame.presenter.view

import qiaoping.com.kotlintest.frame.bean.UserInfoBean

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
interface TestView {

    fun onSuccess(datas:List<UserInfoBean>?)
}