package qiaoping.com.kotlintest.business.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.lhh.apst.library.CustomPagerSlidingTabStrip
import qiaoping.com.kotlintest.R
import qiaoping.com.kotlintest.business.fragment.HomeFragment
import qiaoping.com.kotlintest.business.fragment.SecondFragment
import qiaoping.com.kotlintest.business.fragment.ThirdFragment
import qiaoping.com.kotlintest.frame.base.KBaseActivity
import qiaoping.com.kotlintest.frame.widget.APSTSViewPager

class MainActivity : KBaseActivity(), ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    private lateinit var pagerSlidingTab: CustomPagerSlidingTabStrip
    private lateinit var apstsViewPager: APSTSViewPager

    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initWidget(savedInstanceState: Bundle?) {
        super.initWidget(savedInstanceState)
         pagerSlidingTab = findViewById(R.id.pager_sliding_tab)
         apstsViewPager = findViewById(R.id.apsts_view_pager)
        initTabNameAndIcon()
    }

    private fun initTabNameAndIcon() {
        val fragments= mutableListOf(HomeFragment(),SecondFragment(),ThirdFragment())
        val adapter = DotFragmentPagerAdapter(mContext,supportFragmentManager,fragments)
        apstsViewPager.offscreenPageLimit=fragments.size
        apstsViewPager.adapter=adapter
        pagerSlidingTab.setViewPager(apstsViewPager)
        pagerSlidingTab.setOnPageChangeListener(this)
    }
}
