package com.smartcity.qiuchenly.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcity.qiuchenly.R;
import com.smartcity.qiuchenly.function.iNavigation_items_Click;

import java.util.List;

/**
 * Author        : chenyuqi
 * Times         : 2017 年 11 月 19 日 14:46
 * Usage         :
 */

public class mFun_nav_RlAdapter
        extends RecyclerView.Adapter<mFun_nav_RlAdapter.VH>
        implements View.OnClickListener {

  List<String> list_title;
  private iNavigation_items_Click iNavigation;

  public mFun_nav_RlAdapter(List<String> list_title, iNavigation_items_Click iNavigation) {
    this.list_title = list_title;
    this.iNavigation = iNavigation;
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.fun_navigation_items,
            parent,
            false);
    return new VH(v);
  }

  @Override
  public void onBindViewHolder(VH v, int p) {
    v.tv_title.setText(list_title.get(p));
    v.v_content.setTag(p);
    v.v_content.setOnClickListener(this);
  }

  @Override
  public int getItemCount() {
    return list_title.size();
  }

  @Override
  public void onClick(View view) {
    int p = (int) view.getTag();
    iNavigation.navigation_itemsClickPosition(p);
  }

  class VH extends RecyclerView.ViewHolder {
    TextView tv_title;
    View v_content;

    public VH(View v) {
      super(v);
      v_content = v;
      tv_title = v.findViewById(R.id.fun_navigation_items_title);
    }
  }
}
