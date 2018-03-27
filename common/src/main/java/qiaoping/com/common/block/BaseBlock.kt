package qiaoping.com.common.block

import android.content.Context

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
open class BaseBlock {
    val context:Context
    constructor(context: Context) {
        this.context = context
    }
}