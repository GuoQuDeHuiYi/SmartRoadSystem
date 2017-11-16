package com.smartcity.qiuchenly;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;
import com.smartcity.qiuchenly.Base.ShareUtils;

public class View_LoginPage extends BaseActivity {

    Toolbar toolbar = null;
    EditText user, pass;
    Button m_LoginView_mLogin;
    //网络设置按钮
    RelativeLayout m_LoginView_NetSetting;

    //记住密码，自动登录
    CheckBox m_LoginView_rememberPass, m_LoginView_autoLogin;


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public ActivitySet getLayoutSetting() {
        ActivitySet set = new ActivitySet();
        set.noClickBack = false;
        set.doubleClickExitActivity = true;
        set.TranslateBar = true;
        return set;
    }

    @Override
    public void ready() {

        //go(View_mainPage.class,3000,true);
    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.m_LoginView_mLogin:
                //需要验证用户名和密码的 此处跳过
                ShareUtils.put("isLogin", true);
                go(View_mainPage.class, 2000, true);
                break;
            case R.id.m_LoginView_mRegister:
                Msg("想注册？你怕是石乐志。");
                break;
        }
    }

    @Override
    public void findID() {
        user = find(R.id.UserName);
        pass = find(R.id.passWord);
        m_LoginView_mLogin = find(R.id.m_LoginView_mLogin, true);
        toolbar = find(R.id.toolbar);
        setSupportActionBar(toolbar);
        m_LoginView_NetSetting = find(R.id.m_LoginView_NetSetting);
        m_LoginView_rememberPass = find(R.id.m_LoginView_rememberPass);
        m_LoginView_autoLogin = find(R.id.m_LoginView_autoLogin);
    }
}
