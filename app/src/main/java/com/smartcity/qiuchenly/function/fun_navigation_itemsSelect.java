package com.smartcity.qiuchenly.function;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.smartcity.qiuchenly.Adapter.mFun_nav_RlAdapter;
import com.smartcity.qiuchenly.R;

import java.util.List;

/**
 * Author        : chenyuqi
 * Times         : 2017 年 11 月 19 日 14:37
 * Usage         :托管主页面侧滑菜单的items选择和数据填充
 */

public class fun_navigation_itemsSelect {
    private List<String> list;
    private AppCompatActivity content_View;
    RecyclerView RecyclerView;

    public fun_navigation_itemsSelect(List<String> list,
                                      AppCompatActivity content_View,
                                      iNavigation_items_Click iNavigation_items_Click) {
        this.list = list;
        this.content_View = content_View;
        mFun_nav_RlAdapter adapter = new mFun_nav_RlAdapter(this.list,iNavigation_items_Click);
        RecyclerView = content_View.findViewById(
                R.id.navigation_body_selectItems);
        RecyclerView.setHasFixedSize(false);
        RecyclerView.setLayoutManager(
                new LinearLayoutManager(content_View));
        RecyclerView.setAdapter(adapter);
    }
}
