package com.smartcity.qiuchenly.Base;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Author: QiuChenly
 * Date   : 2017/10/11
 * Usage :基本类,所有Activity应继承此类
 * Lasted:2017 10 11
 * ProjectName:2
 * Create: 2017 10 11 , on 11:06
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

  //define one Handler Object for using
  Handler handler = null;

  //here set Content layout
  public abstract int getLayout();

  //here set Layout Content Setting
  public abstract ActivitySet getLayoutSetting();

  //this is setDoubleClick
  private boolean doubleClickExit, noClickBack;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    handler = new Handler(getMainLooper());
    ActivitySet mSet = getLayoutSetting();
    if (mSet.TranslateBar) {
      ActionBar bar = getActionBar();
      if (bar != null) {
        bar.hide();
      }


      //FIX translateBarSetting
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
      }
    }
    doubleClickExit = mSet.doubleClickExitActivity;
    noClickBack = mSet.noClickBack;
    findID();
    ready();
  }

  public abstract void ready();

  //this is delays
  private long upTime = 0, now;

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    //set Double Click with exit application
    if (doubleClickExit) {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
        if ((now = System.currentTimeMillis()) - upTime > 2000) {
          Msg("press again");
          upTime = now;
        } else {
          System.exit(0);
        }
      }
      return true;
    } else if (noClickBack) {
      return true;
    } else {
      return super.onKeyUp(keyCode, event);
    }
  }

  /**
   * @param v
   */
  public abstract void click(View v);

  public abstract void findID();

  public <T extends View> T find(int id) {
    T t = super.findViewById(id);
    return t;
  }

  public <T extends View> T find(int id, boolean setThisOnClick) {
    T t = super.findViewById(id);
    if (setThisOnClick) {
      t.setOnClickListener(this);
    }
    return t;
  }

  @Override
  public void onClick(View view) {
    click(view);
  }

  public <T> void Msg(T t) {
    Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
  }

  public <T> void Msg(T t, boolean isLong) {
    Toast.makeText(this, t.toString(), isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
  }

  public <T> void go(Class<T> activity, boolean isClosed, final boolean allowFinish) {
    startActivity(new Intent(this, activity));
    if (isClosed)
      finish();
    goAnimation();
  }

  public <T> void go(Class<T> activity, final boolean allowFinish) {
    startActivity(new Intent(this, activity));
    if (allowFinish) {
      finish();
    }
    goAnimation();
  }

  public <T> void go(Class<T> activity, long delayMillis, final boolean allowFinish) {
    final Intent mIntent = new Intent(this, activity);

//    handler.post(new Runnable() {
//      @Override
//      public void run() {
//        finish();
//      }
//    });
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(mIntent);
        goAnimation();
        if (allowFinish) {
          finish();
        }
      }
    }, delayMillis);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  void goAnimation() {
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
  }
}
