package qiaoping.com.common.block

import android.content.Context
import qiaoping.com.common.KotlinApplication
import qiaoping.com.common.utils.SharePreferenceUtil

/**
 * Author: qiaoping.xiao  on 2018/3/19.
 * description:
 */
class LoginBlock(context: Context) : BaseBlock(context) {


    companion object {

        /**用户登录token */
        private var mAuthToken: String? = null

        /**
         * 获得登陆token
         */
        fun getAuthToken(): String? {
            if (mAuthToken == null) {
                mAuthToken = SharePreferenceUtil.getString(KotlinApplication.getContext(), SharePreferenceUtil.USER_TOKEN)
            }
            return mAuthToken
        }

        /**
         * 设置登陆token
         */
        fun setAuthToken(authToken: String?) {
            mAuthToken = authToken
            if (authToken != null) {
                SharePreferenceUtil.putString(KotlinApplication.getContext(), SharePreferenceUtil.USER_TOKEN, authToken)
            }
        }
    }
}
