package com.smartcity.qiuchenly.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.qiuchenly.Adapter.iController.iContentViewPagerViewEvent;

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
  iContentViewPagerViewEvent event;

  public mContentViewPager(List<View> lists, iContentViewPagerViewEvent event) {
    this.event = event;
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
    event.setViewEvent(v);
    container.addView(v);
    return v;
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
