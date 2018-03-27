package qiaoping.com.kotlintest.frame.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import qiaoping.com.kotlintest.frame.base.application.ApplicationHelper
import qiaoping.com.kotlintest.frame.utils.createLoadingDialog
import java.util.HashMap

/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:
 */
abstract class KBaseFragment:BaseFragment() {
    private val TAG = javaClass.simpleName
    protected var mSelectIndex: Int = 0
    protected var mIndex: Int = 0
    protected var mHaveIndex: Boolean = false
    protected var isFirst: Boolean = false
    private var mHandler: Handler? = null
    private var mWaittingDialog: Dialog? = null
    private val mNoNetworkView: View? = null
    private var mNoDataView: View? = null
    protected var mKContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isFirst = true
    }

    abstract override fun getContentLayoutId(): Int 
    
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            this.initFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (this.mIndex == this.mSelectIndex && this.mHaveIndex) {
            this.initFragment()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.mWaittingDialog != null) {
            this.mWaittingDialog!!.dismiss()
        }
    }


    fun HxBaseFragment(index: Int) {
        this.mIndex = index
        this.mHaveIndex = true
    }

    fun setFragmentIndex(index: Int) {
        this.mIndex = index
        this.mHaveIndex = true
    }

    fun setSelectIndex(index: Int) {
        this.mSelectIndex = index
    }

    fun getFragmentIndex(): Int {
        return if (this.mHaveIndex) this.mIndex else 0
    }

    fun initFragment() {
        try {
            if (this.mRootView == null || this.context == null) {
                return
            }

            if (this.isFirst) {
                try {
                    this.initValue()
                } catch (var6: Exception) {
                    var6.printStackTrace()
                }

                try {
                    this.initWidget()
                } catch (var5: Exception) {
                    var5.printStackTrace()
                }

                try {
                    this.initListener()
                } catch (var4: Exception) {
                    var4.printStackTrace()
                }

                try {
                    this.initData()
                } catch (var3: Exception) {
                    var3.printStackTrace()
                }

                try {
                    this.resume()
                } catch (var2: Exception) {
                    var2.printStackTrace()
                }

                this.isFirst = false
            } else {
                this.resume()
            }
        } catch (var7: Exception) {
            var7.printStackTrace()
        }

    }

    protected fun initValue() {}

    protected fun initData() {}

    protected fun initWidget() {}

    protected fun initListener() {}

    protected fun resume() {}

    fun getHandler(): Handler {
        return Handler(ApplicationHelper.getApplication()!!.mainLooper)
    }

    fun showDialog() {
        this.getHandler().post {
            if (getActivity() != null && !getActivity().isDestroyed()) {
                if (mWaittingDialog == null) {
                    mWaittingDialog = createLoadingDialog(mContext, "")
                }

                mWaittingDialog!!.show()
            }
        }
    }

    fun dismissDialog() {
        this.getHandler().post {
            if (mWaittingDialog != null && mWaittingDialog!!.isShowing()) {
                mWaittingDialog!!.dismiss()
            }
        }
    }

    fun showNoDataNoti(viewGroup: ViewGroup, view: View) {
        try {
            val lp: ViewGroup.LayoutParams
            if (this.mNoDataView == null) {
                this.mNoDataView = view
                this.mNoDataView!!.setOnClickListener { }
                lp = ViewGroup.LayoutParams(-1, -1)
                viewGroup.addView(this.mNoDataView, lp)
            } else if (this.mNoDataView === view) {
                this.mNoDataView!!.visibility = View.VISIBLE
            } else {
                viewGroup.removeView(this.mNoDataView)
                this.mNoDataView = view
                lp = ViewGroup.LayoutParams(-1, -1)
                viewGroup.addView(this.mNoDataView, lp)
                this.mNoDataView!!.visibility = View.VISIBLE
            }
        } catch (var4: Exception) {
        }

    }

    fun hideNoNetworkNoti() {}

    fun showNoDataNoti(viewGroup: ViewGroup, layoutResId: Int) {
        try {
            if (this.mNoDataView == null) {
                this.mNoDataView = this.mInflater.inflate(layoutResId, null as ViewGroup?)
                this.mNoDataView!!.setOnClickListener { }
                val lp = ViewGroup.LayoutParams(-1, -1)
                viewGroup.addView(this.mNoDataView, lp)
            } else {
                this.mNoDataView!!.visibility = View.VISIBLE
            }
        } catch (var4: Exception) {
        }

    }

    fun hideNoDataNoti() {
        if (this.mNoDataView != null) {
            this.mNoDataView!!.visibility = View.GONE
        }

    }

    fun reRequestData() {}

    fun scrollToTop() {}

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

}