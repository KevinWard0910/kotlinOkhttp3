package qiaoping.com.kotlintest.frame.base

import android.annotation.SuppressLint
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import qiaoping.com.kotlintest.R
import qiaoping.com.kotlintest.frame.constant.CACHE_IMG
import java.io.File
import java.io.FileOutputStream

/**
 * Author: qiaoping.xiao  on 2018/3/13.
 */
abstract class KBaseActivity : BaseActivity() {
     val TAG:String = javaClass.simpleName
    var mPicName = ""
    lateinit var mContext:Context
    var mRelBack:RelativeLayout? = null
    lateinit var linWrapper:LinearLayout
    var isSwitchFragmenting:Boolean = false


    fun saveBitmap(bm:Bitmap,picName:String):String?{
        if (picName.isEmpty())
            mPicName = "temp"
        else
            mPicName = picName

        val file:File = File(CACHE_IMG,mPicName)
        if (file.exists()){
            file.delete()
        }
        try {
            val outFile = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG,90,outFile)
            outFile.flush()
            outFile.close()
            return CACHE_IMG+mPicName
        }catch (e:Exception){
            Log.e(TAG,"图片压缩出错啦，坐标：${TAG}——>${e.message}")
        }
        return ""
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish()
        }
        return false
    }

    override fun getHeaderLayoutId(): Int {
        return R.layout.activity_base_k_header
    }
//    override fun getContentLayoutId(): Int {
//        return R.layout.activity_base_k_content
//    }

    override fun initValue(savedInstanceState: Bundle?) {
        mContext = this
        try {
        if (getHeaderView() != null){
            mRelBack = findViewById(R.id.rel_back)
        }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun initWidget(savedInstanceState: Bundle?) {
        //TODO 侧滑退出功能初始化
    }

    override fun initListener(savedInstanceState: Bundle?) {
        mRelBack?.setOnClickListener {
            finish()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    @SuppressLint("CommitTransaction")
    fun replaceFragment(resLayId:Int, fragment:Fragment, isAddBackStack:Boolean){
        if (!isSwitchFragmenting){
            isSwitchFragmenting = true
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right,R.anim.slide_in_left,R.anim.slide_out_right)

        }
    }
}