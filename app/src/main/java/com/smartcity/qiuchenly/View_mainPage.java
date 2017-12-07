package com.smartcity.qiuchenly;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartcity.qiuchenly.Adapter.iController.*;
import com.smartcity.qiuchenly.Adapter.*;
import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;
import com.smartcity.qiuchenly.Base.ShareUtils;
import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.DataModel.userManageModel;
import com.smartcity.qiuchenly.Net.iCallback;
import com.smartcity.qiuchenly.Presenter.loginPresenter;
import com.smartcity.qiuchenly.function.fun_navigation_itemsSelect;
import com.smartcity.qiuchenly.function.iNavigation_items_Click;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.smartcity.qiuchenly.Base.Utils.userInfo;

public class View_mainPage extends BaseActivity implements iContentPageChanged,
        iContentViewPagerViewEvent, iCallback.getUserManageData,
        iPersonalEvent, iNavigation_items_Click,
        iPersonPageChanged {

  private static final String TAG = "QiuChen";

  FrameLayout menu;
  DrawerLayout draw;
  boolean isOpen = false;

  //首页标题修改
  TextView mMainContentView_title;
  //首页上方工具栏
  LinearLayout mUser_Manage_items_tools;
  //工具栏2个按钮
  Button mUser_manage_tools_allPay, mUser_manage_tools_PayHistory, infoBtn;

  //下方ViewPager
  ViewPager mMainContentView, Personal_ViewPager;

  RelativeLayout mContentRl;

  ImageView contentBitmap;

  Toolbar mMainToolBar;

  loginPresenter presenter;

  EditText AmountValue;

  Button SetAmount;

  TextView Amount, Personal_TextView, Prepaid_TextView, Threshold_TextView, Personal_user,
          Personal_sex, Personal_cardID, Personal_phoneNum, Personal_time;


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

  int Personal_Center_INDEX;

  @Override
  public void ready() {
    int[] viewsCollection = new int[]{
            R.layout.model_usermanage,
            R.layout.model_businquery,
            R.layout.model_lightmanage,
            R.layout.model_car_break_rules_and_regulations,
            R.layout.model_personal_center
    };

    String[] viewTitle = new String[]{
            "用户管理", "公交查询",
            "红绿灯管理", "违章查询", "个人中心"
    };


    List<String> viewsTitles = new ArrayList<>();
    List<View> listView = new ArrayList<>();
    for (int i = 0; i < viewsCollection.length; i++) {
      //判断个人中心的序号
      if (viewsCollection[i] == R.layout.model_personal_center) {
        Personal_Center_INDEX = i;
      }

      View v = LayoutInflater.from(this).inflate(viewsCollection[i], null);
      //添加布局
      listView.add(v);
      //名称
      viewsTitles.add(viewTitle[i]);
    }
    mContentViewPager ViewPager = new mContentViewPager(listView, this);
    mMainContentView.setAdapter(ViewPager);
    mMainContentView.setOffscreenPageLimit(10);
    mMainContentView.setOnPageChangeListener(new mContentView_SwitchView(this, viewsTitles));
    mMainContentView_title.setText(viewTitle[0]);

    Bitmap bits = BitmapFactory.decodeResource(getResources(), R.mipmap.maincontent);
    for (int a = 0; a < 5; a++) {
      bits = Utils.blur(bits, 17f);
    }
    contentBitmap.setImageBitmap(bits);


    //UI适配初始化
    final ViewTreeObserver viewTreeObserver = mContentRl.getViewTreeObserver();

    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @SuppressLint("NewApi")
      @Override
      public void onGlobalLayout() {
        mContentRl.buildDrawingCache();
        Bitmap bit = mContentRl.getDrawingCache();
        for (int a = 0; a < 50; a++) {
          bit = Utils.blurBitmapRender(bit, mMainToolBar, 25f, 2);
        }
        mMainToolBar.setBackground(new BitmapDrawable(bit));
        mContentRl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
    });
    presenter = new loginPresenter();
    fun_navigation_itemsSelect = new fun_navigation_itemsSelect
            (viewsTitles, this, this);
  }

  fun_navigation_itemsSelect fun_navigation_itemsSelect;


  @Override
  public void click(View v) {
    switch (v.getId()) {
      case R.id.menu:
        draw.openDrawer(Gravity.START);
        isOpen = true;
        break;
      case R.id.user_manage_tools_allPay:
        批量支付();
        break;
      case R.id.user_manage_tools_PayHistory:
        mMainContentView.setCurrentItem(Personal_Center_INDEX);
        click(Prepaid_TextView);
        break;
      case R.id.Personal_TextView:
        Personal_ViewPager.setCurrentItem(0);
        break;
      case R.id.Prepaid_TextView:
        Personal_ViewPager.setCurrentItem(1);
        break;
      case R.id.Threshold_TextView:
        Personal_ViewPager.setCurrentItem(2);
        break;
      default:

        break;
    }
  }

  void 批量支付() {
    Map<Integer, Boolean> list = mAdapter.getCheckedItems();
    userManageModel user = mAdapter.getItems();
    Iterator<Map.Entry<Integer, Boolean>> s = list.entrySet().iterator();
    List<userManageModel.datas> result = new ArrayList<>();
    StringBuilder res = new StringBuilder();
    while (s.hasNext()) {
      Map.Entry<Integer, Boolean> a = s.next();
      if (a.getValue()) {
        result.add(user.data[a.getKey()]);
        res.append(user.data[a.getKey()].carID).append(",");
        Log.d(TAG, "批量支付: " + user.data[a.getKey()].carID);
      }
    }
    Msg("批量支付的车牌号对象:" + res);
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
    mMainToolBar = find(R.id.mMainToolBar);
    contentBitmap = find(R.id.contentBitmap);
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
  public TextView getContentTitleView(int position) {
    if (position == 0) {
      mUser_Manage_items_tools.setVisibility(View.VISIBLE);
    } else {
      mUser_Manage_items_tools.setVisibility(View.INVISIBLE);
    }
    return this.mMainContentView_title;
  }

  RecyclerView user_manage_items_recyclerView;

  mContentRecyclerViewAdapter mAdapter;

  LinearLayout hospital, hospitalInfo, lenovo, lenovoInfo, user, bus, light, lllegal;

  ImageView hospitalImg, lenovoImg;

  @Override
  public void setViewEvent(int i, View view) {
    switch (i) {
      case 0:
        user_manage_items_recyclerView = view.findViewById(R.id.user_manage_items_recyclerView);
        user_manage_items_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user_manage_items_recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
          @Override
          public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = 15;
            outRect.left = 15;
            outRect.right = 15;
          }
        });
        mAdapter = new mContentRecyclerViewAdapter(new userManageModel());
        mAdapter.setOnItemClickListener(new mContentRecyclerViewAdapter.onItemClickListener() {
          @Override
          public void setOnClickListener(View v, int position) {
            mAdapter.setItemChecked(position);
          }

          @Override
          public void setOnLongClickListener(View v, int position) {
            mAdapter.setItemChecked(position);
            mAdapter.notifyDataSetChanged();
          }
        });
        user_manage_items_recyclerView.setAdapter(mAdapter);
        presenter.getManageUser(this);
        //运行闪退分析
        //1.发现在旋转屏幕后会随机闪退。
        //2.运行一段时间后旋转屏幕随机闪退。
        //分析：检查Logcat信息，发现闪退原因是：在子线程中更新了主线程控件UI
        //产生跨进程操作导致OOM

        //出错状态还原：
        //APP打开后，手机默认是竖屏，此时用户选择旋转屏幕切换视图。
        //视图切换后，UI需要更新自动适配APP。
        //UI更新将重新调用getMainUser()方法，填充数据
        //此时将回调getDataSuccess()或getDataFailed()方法。
        //调用getMainUser()方法时，new Thread()开始异步请求。
        //问题就出在这：当异步请求结束后，运行在Thread里面的代码开始通知RecyclerView进行数据更新
        //当执行notifyDataSetChanged()方法时，异步请求中Thread代码跨线程操作UI线程中的控件，此时系统捕捉到错误信息。
        //
        //解决方法：使用主线程更新控件信息。
        break;
      case 1:
        lenovo = view.findViewById(R.id.lenovoStation);
        hospital = view.findViewById(R.id.hospitalStation);
        lenovoInfo = view.findViewById(R.id.lenovoStationInfo);
        hospitalInfo = view.findViewById(R.id.hospitalStationInfo);
        lenovoImg = view.findViewById(R.id.lenovoStationImg);
        hospitalImg = view.findViewById(R.id.hospitalStationImg);
        infoBtn = view.findViewById(R.id.infoBtn);

        hospital.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (hospitalInfo.getVisibility() == View.VISIBLE) {
              hospitalInfo.setVisibility(View.GONE);
              hospitalImg.setImageResource(R.drawable.arrow_left);
            } else {
              hospitalInfo.setVisibility(View.VISIBLE);
              hospitalImg.setImageResource(R.drawable.arrow_bottom);
            }
          }
        });
        lenovo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (lenovoInfo.getVisibility() == View.VISIBLE) {
              lenovoInfo.setVisibility(View.GONE);
              lenovoImg.setImageResource(R.drawable.arrow_left);
            } else {
              lenovoInfo.setVisibility(View.VISIBLE);
              lenovoImg.setImageResource(R.drawable.arrow_bottom);
            }
          }
        });
        infoBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            showDialog();
          }
        });
        break;
      case 4:
        InitPersonal(view);
        break;
      default:
        break;
    }
  }

  void InitPersonal(View view) {
    Personal_ViewPager = view.findViewById(R.id.Personal_viewpager);
    List<View> list = new ArrayList<>();
    for (int a = 0; a < 3; a++) {
      View v = LayoutInflater.from(this).inflate(a == 1 ? R.layout.prepaid : a == 2 ?
              R.layout.threshold : R.layout.personal, null);
      list.add(v);
    }
    Personal_ViewPager.setOffscreenPageLimit(3);
    Personal_ViewPager.setAdapter(new mPersonal_center_ViewPager(list, this));
    Personal_ViewPager.addOnPageChangeListener(new mPersonView_SwitchView(this));

    Personal_TextView = view.findViewById(R.id.Personal_TextView);
    Prepaid_TextView = view.findViewById(R.id.Prepaid_TextView);
    Threshold_TextView = view.findViewById(R.id.Threshold_TextView);
    Personal_TextView.setOnClickListener(this);
    Prepaid_TextView.setOnClickListener(this);
    Threshold_TextView.setOnClickListener(this);

    personPageChangeTitle = new ArrayList<>();
    personPageChangeTitle.add(Personal_TextView);
    personPageChangeTitle.add(Prepaid_TextView);
    personPageChangeTitle.add(Threshold_TextView);
  }

  public void showDialog() {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_businfo, null);
    dialog.setView(dialogView);
    dialog.show();
  }


  @Override
  public void getDataSuccess(final userManageModel data) {
    mAdapter.addListData(data);
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void getDataFailed(String errReason) {
    Msg(errReason);
  }

  @Override
  public void PersonSetViewEvent(View v, int p) {
    switch (p) {
      case 0:
        Personal_user = v.findViewById(R.id.tv_personal_user);
        Personal_cardID = v.findViewById(R.id.tv_personal_cardID);
        Personal_phoneNum = v.findViewById(R.id.tv_personal_phoneNum);
        Personal_sex = v.findViewById(R.id.tv_personal_sex);
        Personal_time = v.findViewById(R.id.tv_personal_time);

        Personal_user.setText(userInfo.userName);
        Personal_cardID.setText(userInfo.cardID);
        Personal_phoneNum.setText(userInfo.phoneNum);
        Personal_sex.setText(userInfo.sex);
        Personal_time.setText(userInfo.regTime);

        break;
      case 1:
        break;
      case 2:
        SetAmount = v.findViewById(R.id.SetAmount);
        Amount = v.findViewById(R.id.Amount);
        AmountValue = v.findViewById(R.id.AmountValue);
        int a = Utils.getMoneyLimitValue();
        Amount.setText(a + "");
        SetAmount.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Amount.setText(AmountValue.getText().toString());
            //FIXME:数据用int保存,而非String类型!!
            ShareUtils.put("getMoneyLimitValue",
                    (int) Integer.valueOf(Amount.getText().toString()));
            mAdapter.notifyDataSetChanged();
            AmountValue.setText("");
          }
        });

        break;
    }
  }

  @Override
  public void navigation_itemsClickPosition(int position) {
    mMainContentView.setCurrentItem(position);
    draw.closeDrawer(Gravity.START);
  }

  List<TextView> personPageChangeTitle;

  @Override
  public void PersonPageChangedEvent(int p) {
    for (int a = 0; a < personPageChangeTitle.size(); a++) {
      personPageChangeTitle.get(a).setTextColor(Color.WHITE);
    }
    personPageChangeTitle.get(p).setTextColor(Color.RED);
  }
}
