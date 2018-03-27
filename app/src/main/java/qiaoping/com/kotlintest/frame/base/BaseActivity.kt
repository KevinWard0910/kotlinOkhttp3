package qiaoping.com.kotlintest.frame.base

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import qiaoping.com.kotlintest.R

/**
 * Author: qiaoping.xiao  on 2018/3/13.
 */
abstract class BaseActivity: FragmentActivity() {
    var headview:View? = null
    private lateinit var contentview:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val layoutInflater= LayoutInflater.from(this)
        val rootView = findViewById<LinearLayout>(R.id.ll_root)
        if (getHeaderLayoutId()>-1){
            headview = layoutInflater.inflate(getHeaderLayoutId(), null)
            rootView.addView(headview,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        if (getContentLayoutId()>-1){
            contentview = layoutInflater.inflate(getContentLayoutId(), null)
            rootView.addView(contentview,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        }


        initValue(savedInstanceState)
        initWidget(savedInstanceState)
        initListener(savedInstanceState)
        initData(savedInstanceState)

    }

    abstract fun getHeaderLayoutId(): Int

    abstract fun getContentLayoutId(): Int


    abstract fun initValue(savedInstanceState: Bundle?)

    abstract fun initWidget(savedInstanceState: Bundle?)

    abstract fun initListener(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)
    open fun getHeaderView():View?{
        return headview
    }

    open fun ShowOrHideHeaderView(isShow:Boolean){
        if(isShow)
            headview?.visibility=View.VISIBLE
        else
            headview?.visibility=View.GONE
    }
}