package com.smartcity.qiuchenly;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartcity.qiuchenly.Adapter.iContentPageChanged;
import com.smartcity.qiuchenly.Adapter.mContentViewPager;
import com.smartcity.qiuchenly.Adapter.mContentView_SwitchView;
import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseActivity implements iContentPageChanged {

  FrameLayout menu;
  DrawerLayout draw;
  boolean isOpen = false;

  //首页标题修改
  TextView mMainContentView_title;
  //首页上方工具栏
  LinearLayout mUser_Manage_items_tools;
  //工具栏2个按钮
  Button mUser_manage_tools_allPay, mUser_manage_tools_PayHistory;

  //下方ViewPager
  ViewPager mMainContentView;

  RecyclerView mContentRl;


  @Override
  public int getLayout() {
    return R.layout.activity_second;
  }

  @Override
  public ActivitySet getLayoutSetting() {
    ActivitySet set = new ActivitySet();
    set.TranslateBar = true;
    set.noClickBack = false;
    set.doubleClickExitActivity = true;
    return set;
  }

  @Override
  public void ready() {

    //UI适配初始化
    final ViewTreeObserver viewTreeObserver = mContentRl.getViewTreeObserver();

    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @SuppressLint("NewApi")
      @Override
      public void onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this);

        mContentRl.buildDrawingCache();

        Bitmap bit = mContentRl.getDrawingCache();

        

      }
    });


    int[] viewsCollection = new int[]{
            R.layout.model_usermanage,
            R.layout.model_businquery,
            R.layout.model_lightmanage,
            R.layout.model_car_break_rules_and_regulations
    };

    String[] viewTitle = new String[]{
            "用户管理", "公交查询",
            "红绿灯管理", "违章查询"
    };

    List<String> viewsTitles = new ArrayList<>();
    List<View> listView = new ArrayList<>();
    for (int i = 0; i < viewsCollection.length; i++) {
      View v = LayoutInflater.from(this).inflate(viewsCollection[i], null);
      listView.add(v);
      viewsTitles.add(viewTitle[i]);
    }
    mContentViewPager ViewPager = new mContentViewPager(listView);
    mMainContentView.setAdapter(ViewPager);
    mMainContentView.setOffscreenPageLimit(10);
    mMainContentView.setOnPageChangeListener(new mContentView_SwitchView(this, viewsTitles));
    mMainContentView_title.setText(viewTitle[0]);
  }


  @Override
  public void click(View v) {
    switch (v.getId()) {
      case R.id.menu:
        draw.openDrawer(Gravity.START);
        isOpen = true;
        break;
      case R.id.user_manage_tools_allPay:
        Msg("支付？不存在的");
        break;
      case R.id.user_manage_tools_PayHistory:
        Msg("穷鬼！心悦3有了吗？没有还敢谈充钱两个字？？");
        break;
      default:

        break;
    }
  }

  @Override
  public void findID() {
    menu = find(R.id.menu, true);
    draw = find(R.id.drawerlayout);
    mMainContentView_title = find(R.id.mMainContentView_title);
    mMainContentView = find(R.id.mMainContentView);
    mUser_Manage_items_tools = find(R.id.user_Manage_items_tools);
    mUser_manage_tools_allPay = find(R.id.user_manage_tools_allPay, true);
    mUser_manage_tools_PayHistory = find(R.id.user_manage_tools_PayHistory, true);
    mContentRl = find(R.id.mContentRl);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (isOpen) {
      draw.closeDrawer(Gravity.START);
      isOpen = false;
      return true;
    }
    return super.onKeyUp(keyCode, event);
  }

  @Override
  public TextView getContentTitleView() {
    return this.mMainContentView_title;
  }
}
