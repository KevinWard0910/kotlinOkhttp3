package qiaoping.com.kotlintest.frame.utils

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import qiaoping.com.kotlintest.R
import qiaoping.com.kotlintest.frame.constant.DeviceInfo

/**
 * Author: qiaoping.xiao  on 2018/3/14.
 * description:等待框创建
 */

fun createLoadingDialog(context: Context, msg: String): Dialog {
    val v = LayoutInflater.from(context).inflate(R.layout.loaddialog, null as ViewGroup?)
    val layout = v.findViewById(R.id.dialog_view) as LinearLayout
    if (!TextUtils.isEmpty(msg)) {
        val tvMsg = layout.findViewById(R.id.tipTextView) as TextView
        tvMsg.text = msg
    }

    val loadingDialog = Dialog(context, R.style.loading_dialog)
    loadingDialog.setCanceledOnTouchOutside(false)
    loadingDialog.setContentView(layout, LinearLayout.LayoutParams(DeviceInfo.WIDTHPIXELS / 3 * 2, -2))
    return loadingDialog
}