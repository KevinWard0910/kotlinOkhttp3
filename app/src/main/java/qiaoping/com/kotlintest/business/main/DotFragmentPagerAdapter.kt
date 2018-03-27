package qiaoping.com.kotlintest.business.main

import android.content.Context
import android.graphics.Rect
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lhh.apst.library.CustomPagerSlidingTabStrip
import com.lhh.apst.library.ViewHolder
import qiaoping.com.kotlintest.R
import qiaoping.com.kotlintest.frame.base.KBaseFragment

@Suppress("NAME_SHADOWING")
/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:
 */
class DotFragmentPagerAdapter(context: Context, fm: FragmentManager, datas: MutableList<KBaseFragment>) : FragmentStatePagerAdapter(fm), CustomPagerSlidingTabStrip.CustomTabProvider {

    private val VIEW_FIRST = 0
    private val VIEW_SECOND = 1
    private val VIEW_THIRD = 2
    private val VIEW_FOURTH = 3
    private var mDatas: MutableList<KBaseFragment> = datas
    protected var mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): KBaseFragment? {
        if (position >= 0 && position < mDatas.size) {
            return when (position) {
                VIEW_FIRST, VIEW_SECOND, VIEW_THIRD, VIEW_FOURTH ->
                    mDatas[position]
                else -> null
            }
        }
        return null

    }

    override fun getPageTitle(position: Int): CharSequence {
        if (position >= 0 && position < mDatas.size) {
            return when (position) {
                VIEW_FIRST -> "first"
                VIEW_SECOND -> "second"
                VIEW_THIRD -> "third"
                VIEW_FOURTH -> "fourth"
                else -> {
                    ""
                }
            }
        }
        return ""
    }


    override fun getSelectTabView(position: Int, convertView: View?): View? {
        return if (convertView == null) {
            val mConvertView: View = mInflater.inflate(R.layout.custom_select_tab, null)
            val tv: TextView = ViewHolder.get(mConvertView, R.id.tab_item)
            tv.text = getPageTitle(position)
            mConvertView
        } else {
            val tv: TextView = ViewHolder.get(convertView, R.id.tab_item)
            tv.text = getPageTitle(position)
            convertView
        }
    }

    override fun getDisSelectTabView(position: Int, convertView: View?): View? {

        return if (convertView == null) {
            val mConvertView: View = mInflater.inflate(R.layout.custom_disselect_tab, null)
            val tv: TextView = ViewHolder.get(mConvertView, R.id.tab_item)
            tv.text = getPageTitle(position)
            mConvertView
        } else {
            val tv: TextView = ViewHolder.get(convertView, R.id.tab_item)
            tv.text = getPageTitle(position)
            convertView
        }
    }

}