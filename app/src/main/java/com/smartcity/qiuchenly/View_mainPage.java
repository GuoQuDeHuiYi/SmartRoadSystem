package com.smartcity.qiuchenly;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartcity.qiuchenly.Adapter.iContentPageChanged;
import com.smartcity.qiuchenly.Adapter.iContentViewPagerViewEvent;
import com.smartcity.qiuchenly.Adapter.mContentRecyclerViewAdapter;
import com.smartcity.qiuchenly.Adapter.mContentViewPager;
import com.smartcity.qiuchenly.Adapter.mContentView_SwitchView;
import com.smartcity.qiuchenly.Base.ActivitySet;
import com.smartcity.qiuchenly.Base.BaseActivity;
import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.DataModel.userManageModel;
import com.smartcity.qiuchenly.Net.Callback;
import com.smartcity.qiuchenly.Presenter.loginPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class View_mainPage extends BaseActivity implements iContentPageChanged,
        iContentViewPagerViewEvent, Callback.getUserManageData {

    String TAG = "QiuChenDebug";

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
    ViewPager mMainContentView, Personal_ViewPager;

    RelativeLayout mContentRl;

    ImageView contentBitmap;

    Toolbar mMainToolBar;

    loginPresenter presenter;




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
            //通过
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

    }


    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.menu:
                draw.openDrawer(Gravity.START);
                isOpen = true;
                break;
            case R.id.user_manage_tools_allPay:
                批量支付();
                // Msg("支付？不存在的");
                break;
            case R.id.user_manage_tools_PayHistory:
                Msg("穷鬼！心悦3有了吗？没有还敢谈充钱两个字？？");
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
                res.append(user.data[a.getKey()].carID + ",");
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
            case 4:
                InitPersonal(view);
                break;
            default:
                break;
        }
    }

    void InitPersonal(View view) {
        Personal_ViewPager = find(R.id.Personal_viewpager);
//        int[] Personal_
        List<View> list = new ArrayList<>();


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
}
