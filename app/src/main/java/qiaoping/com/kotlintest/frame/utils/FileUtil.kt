package qiaoping.com.kotlintest.frame.utils

import android.annotation.SuppressLint
import android.os.Environment
import android.provider.ContactsContract.Directory.PACKAGE_NAME

/**
 * Author: qiaoping.xiao  on 2018/3/13.
 * description:
 */

val SDCard = Environment.getExternalStorageDirectory().absolutePath!!

@SuppressLint("SdCardPath")
fun getDataPath():String{
    return if(Environment.getExternalStorageState() == "mounted") "$SDCard/Android/data/$PACKAGE_NAME/" else "/data/data/$PACKAGE_NAME/"
}