package com.smartcity.qiuchenly

import android.support.design.widget.Snackbar
import android.view.View
import com.smartcity.qiuchenly.Base.ActivitySet
import com.smartcity.qiuchenly.Base.BaseActivity
import com.smartcity.qiuchenly.Base.ShareUtils
import com.smartcity.qiuchenly.Base.Utils
import com.smartcity.qiuchenly.DataModel.userLoginCallBackModel
import com.smartcity.qiuchenly.Net.iCallback
import com.smartcity.qiuchenly.Presenter.loginPresenter
import kotlinx.android.synthetic.main.activity_main.*

class View_LoginPage_K : BaseActivity(), iCallback.loginCallBack {
    var presenter: loginPresenter? = null

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun getLayoutSetting(): ActivitySet {
        return ActivitySet().apply {
            this.TranslateBar = true
            this.noClickBack = false
            this.doubleClickExitActivity = true
        }
    }

    override fun ready() {
        when (ShareUtils.getBoolean("isLogin")) {
            true -> {
                UserName.setText(ShareUtils.get("userName"))
                passWord.setText(ShareUtils.get("passWord"))
                m_LoginView_rememberPass.isChecked = true
            }
        }
        if (ShareUtils.getBoolean("autoLogin")) click(m_LoginView_mLogin)
        presenter = loginPresenter()
    }

    /**
     * @param v
     */
    override fun click(v: View?) {
        when (v) {
            m_LoginView_mLogin -> {
                presenter?.login(
                        UserName.text.toString(),
                        passWord.text.toString(),
                        this)
            }
            m_LoginView_mRegister -> {
                Snackbar.make(mLoginView, "注册？不存在的。", Snackbar.LENGTH_LONG)
                        .show()
            }
        }
    }

    override fun findID() {
        setSupportActionBar(toolbar)
        //Kotlin 不需要设置FindID
        //设置部分必须的调用
        m_LoginView_mLogin.setOnClickListener(this)
        m_LoginView_mRegister.setOnClickListener(this)
    }

    override fun loginSuccess(result: String?) {}   //无需实现此方法

    override fun loginSuccess(userInfo: userLoginCallBackModel?) {
        if (m_LoginView_rememberPass.isChecked) {
            ShareUtils.put("isLogin", true)
            ShareUtils.put<String>("userName", UserName.text.toString())
            ShareUtils.put<String>("passWord", passWord.text.toString())
        }
        if (m_LoginView_autoLogin.isChecked) {
            ShareUtils.put("autoLogin", true)
        }
        Utils.userInfo = userInfo//数据保存
        go(View_mainPage::class.java, true)
    }

    override fun loginFailed(errReason: String?) {
        Snackbar.make(mLoginView, errReason.toString(), Snackbar.LENGTH_LONG).show()
    }
}
