package com.smartcity.qiuchenly.Adapter;

import android.support.v4.view.ViewPager;

import com.smartcity.qiuchenly.Adapter.iController.iContentPageChanged;

import java.util.List;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 11:29
 */

public class mContentView_SwitchView implements ViewPager.OnPageChangeListener {
  iContentPageChanged change;
  List<String> chars;

  public mContentView_SwitchView(iContentPageChanged change, List<String> chars) {
    this.chars = chars;
    this.change = change;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    change.getContentTitleView(position).setText(chars.get(position));
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }
}
