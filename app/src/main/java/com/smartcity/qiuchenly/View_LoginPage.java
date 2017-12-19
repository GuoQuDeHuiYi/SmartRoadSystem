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
import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.DataModel.userLoginCallBackModel;
import com.smartcity.qiuchenly.Net.iCallback;
import com.smartcity.qiuchenly.Presenter.loginPresenter;

public class View_LoginPage extends BaseActivity implements iCallback.loginCallBack {

  Toolbar toolbar = null;
  EditText user, pass;
  Button m_LoginView_mLogin;
  //网络设置按钮
  RelativeLayout m_LoginView_NetSetting;

  //记住密码，自动登录
  CheckBox m_LoginView_rememberPass, m_LoginView_autoLogin;

  loginPresenter presenter;


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

  private static final String TAG = "QiuChen";

  @Override
  public void ready() {
    //首先判断是否为登录状态
    if (ShareUtils.getBoolean("isLogin")) {
      String User, Pass;
      User = ShareUtils.get("userName");
      Pass = ShareUtils.get("passWord");
      user.setText(User);
      pass.setText(Pass);
      m_LoginView_rememberPass.setChecked(true);
    }
    presenter = new loginPresenter();
    if (ShareUtils.getBoolean("autoLogin")) {
      click(m_LoginView_mLogin);
    }
  }

  @Override
  public void click(View v) {
    switch (v.getId()) {
      case R.id.m_LoginView_mLogin:
        //需要验证用户名和密码的 此处跳过
        presenter.login(user.getText().toString(), pass.getText().toString(), this);
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

  @Override
  public void loginSuccess(String result) {
    //此处无需实现此方法
  }

  @Override
  public void loginSuccess(userLoginCallBackModel userInfo) {
    if (m_LoginView_rememberPass.isChecked()) {
      ShareUtils.put("isLogin", true);
      ShareUtils.put("userName", user.getText().toString());
      ShareUtils.put("passWord", pass.getText().toString());
    }
    if (m_LoginView_autoLogin.isChecked()) {
      ShareUtils.put("autoLogin", true);
    }
    Utils.userInfo = userInfo;//数据保存
    go(View_mainPage.class, true);
  }

  @Override
  public void loginFailed(String errReason) {
    ShareUtils.put("isLogin",false);
    Msg(errReason);
  }
}
