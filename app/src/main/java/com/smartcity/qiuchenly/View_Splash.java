package com.smartcity.qiuchenly;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;
import com.smartcity.qiuchenly.Base.ShareUtils;
import com.smartcity.qiuchenly.Base.ThreadTimer;
import com.smartcity.qiuchenly.Base.Utils;

/**
 * 代码重构,使用强制横屏方法
 * 重写欢迎页逻辑
 * 2017.11.24日
 */

public class View_Splash extends BaseActivity implements Handler.Callback {
  Button jmp = null;

  private static final String TAG = "QiuChen";

  Handler handler;

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

    Utils.mInitDataBase();//初始化数据库
    ShareUtils.getSharePreferences();
    handler = new Handler(this.getMainLooper(), this);
//    1 ScaleAnimation 缩放
//    2 TranslateAnimation 平移
//    3 AlphaAnimation 渐变
//    4 RotateAnimation 旋转

    boolean is = Utils.isTwiceOpen();//是否二次打开

    times = new ThreadTimer(handler, 1000, 3000, false, true) {
      @Override
      public void TimeCallBack(long totalTime) {

        jmp.setText("跳过 (" + totalTime / 1000 + "s)");
        if (totalTime == 0) {
          click(jmp);
        }
      }
    };


    if (is) {
      go(View_LoginPage_K.class, true);
    } else {
      //初始化表
      Utils.dataBaseHelper.mCreateUserManageTabel();
      Utils.dataBaseHelper.mCreateNewPayHistoryTable();
      Utils.dataBaseHelper.mUser_insert(1, "辽A10001", "张三", 0);
      Utils.dataBaseHelper.mUser_insert(2, "辽A10002", "李四", 0);
      Utils.dataBaseHelper.mUser_insert(3, "辽A10003", "王五", 0);
      Utils.dataBaseHelper.mUser_insert(4, "辽A10004", "赵六", 0);
      times.Start();
    }

  }

  ThreadTimer times;

  /**
   * 当findID结束后,被点击的控件响应事件统一管理子程序
   *
   * @param v 被传过来的点击
   *          的View
   */
  @Override
  public void click(View v) {
    switch (v.getId()) {
      case R.id.jmp:
        times.Stop();
        go(View_LoginPage_K.class, true);
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

  @Override
  public boolean handleMessage(Message message) {

    switch (message.what) {

    }
    return true;
  }
}
