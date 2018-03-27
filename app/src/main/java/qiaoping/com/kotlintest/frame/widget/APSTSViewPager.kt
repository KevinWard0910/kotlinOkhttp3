package qiaoping.com.kotlintest.frame.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MenuItem
import android.view.MotionEvent

/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:
 */
class APSTSViewPager:ViewPager {
    var mNoFocus: Boolean = false
    var mSmoothScroll: Boolean = false
    constructor(context: Context,attrs:AttributeSet?):super(context,attrs){
        mNoFocus = false
        mSmoothScroll = true;
    }

    constructor(context: Context):this(context,null)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if(mNoFocus) false else super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (mNoFocus) false else super.onInterceptTouchEvent(ev)
    }

    override fun setCurrentItem(item:Int){
        if (!mSmoothScroll) this.setCurrentItem(item,false) else super.setCurrentItem(item)
    }

    fun setNoFocus(b:Boolean){
        mNoFocus = b
    }

    fun setSmoothScroll(smoothScroll:Boolean){
        mSmoothScroll = smoothScroll
    }
}