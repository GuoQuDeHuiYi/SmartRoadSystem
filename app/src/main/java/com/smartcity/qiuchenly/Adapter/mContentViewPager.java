package com.smartcity.qiuchenly.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.qiuchenly.Base.SharedContext;

import java.util.List;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 00:00
 */

public class mContentViewPager extends PagerAdapter {

  List<View> lists;

  public mContentViewPager(List<View> lists) {
    this.lists = lists;
  }

  @Override
  public int getCount() {
    return lists.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View v = lists.get(position);
    setViewEvent(position, v);
    container.addView(v);
    return v;
  }

  //初始化每一个View的事件bind
  void setViewEvent(int i, View view) {
    switch (i) {
      default:
        break;
    }
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  /**
   * 滑动回调
   *
   * @param position
   * @return
   */
  @Override
  public CharSequence getPageTitle(int position) {
    return super.getPageTitle(position);
  }
}
