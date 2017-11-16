package com.smartcity.qiuchenly;

import android.view.View;
import android.widget.Button;

import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;
import com.smartcity.qiuchenly.Base.ShareUtils;
import com.smartcity.qiuchenly.Base.Utils;

public class View_Splash extends BaseActivity {
  Button jmp = null;

  /**
   * 基本Activity布局设置
   *
   * @return
   */
  @Override
  public int getLayout() {
    return R.layout.activity_splash;
  }


  /**
   * 基本activity设置封装
   *
   * @return
   */
  @Override
  public ActivitySet getLayoutSetting() {
    ActivitySet set = new ActivitySet();
    set.TranslateBar = true;//此选项表示是否透明状态栏
    set.doubleClickExitActivity = false;//此选项设置表示双击退出整个App
    set.noClickBack = true;//设置此选项表示在此界面时点击返回键无其他操作
    return set;
  }

  /**
   * 本子程序作用是当整个Activity初始化完成时执行的操作
   */
  @Override
  public void ready() {

//    1 ScaleAnimation 缩放
//    2 TranslateAnimation 平移
//    3 AlphaAnimation 渐变
//    4 RotateAnimation 旋转
    boolean is = Utils.isTwiceOpen();
    if (is)
      if (ShareUtils.getBoolean("isLogin")) {
        go(View_mainPage.class, 0, true);
        return;
      }
    go(View_LoginPage.class, 3000, true);
  }

  /**
   * 当findID结束后,被点击的控件响应事件统一管理子程序
   *
   * @param v 被传过来的点击的View
   */
  @Override
  public void click(View v) {
    switch (v.getId()) {
      case R.id.jmp:
        go(View_LoginPage.class, true);
        break;
      default:
        break;
    }
  }

  /**
   * 在这里设置控件的findViewById
   */
  @Override
  public void findID() {
    jmp = find(R.id.jmp, true);
  }
}
