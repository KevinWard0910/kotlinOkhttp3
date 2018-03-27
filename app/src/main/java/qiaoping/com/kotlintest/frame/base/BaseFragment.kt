package qiaoping.com.kotlintest.frame.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import qiaoping.com.kotlintest.R

/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:
 */
abstract class BaseFragment:Fragment() {
    protected var mRootView: RelativeLayout? = null
    private var mContentView: View? = null
    private var mTitleView: View? = null
    protected lateinit var mInflater: LayoutInflater
    lateinit var mContext:Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mInflater = inflater
        mContext = activity

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_base, null as ViewGroup?) as RelativeLayout
            if (getHeaderLayoutId() != -1) {
                mTitleView = inflater.inflate(getHeaderLayoutId(), null as ViewGroup?)
                mRootView!!.addView(mTitleView, -1, -2)
            }

            if (getContentLayoutId() != -1) {
                mContentView = inflater.inflate(getContentLayoutId(), null as ViewGroup?) as ViewGroup
                mRootView!!.addView(mContentView, -1, -1)
            }
        }
        if (mRootView!!.parent!=null) {
            val mViewRoot = mRootView!!.parent as ViewGroup
            mViewRoot.removeView(mRootView)
        }
        initValue(savedInstanceState)
        initWidget(savedInstanceState)
        initListener(savedInstanceState)
        initData(savedInstanceState)
        return mRootView
    }


    fun getIdentification(): String {
        return javaClass.toString() + this
    }

    fun getContentView(): View? {
        return mContentView
    }

    fun getHeaderView(): View? {
        return mTitleView
    }

    protected fun initValue(savedInstanceState: Bundle?) {}

    protected fun initWidget(savedInstanceState: Bundle?) {}

    protected fun initListener(savedInstanceState: Bundle?) {}

    protected fun initData(savedInstanceState: Bundle?) {}

    protected abstract fun getContentLayoutId(): Int

    protected fun getHeaderLayoutId(): Int {
        return -1
    }
}