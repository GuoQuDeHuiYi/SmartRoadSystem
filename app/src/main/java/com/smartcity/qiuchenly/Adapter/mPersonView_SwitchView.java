package com.smartcity.qiuchenly.Adapter;

import android.support.v4.view.ViewPager;

import com.smartcity.qiuchenly.Adapter.iController.iPersonPageChanged;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 11:29
 */

public class mPersonView_SwitchView implements ViewPager.OnPageChangeListener {
  iPersonPageChanged change;

  public mPersonView_SwitchView(iPersonPageChanged change) {
    this.change = change;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    change.PersonPageChangedEvent(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }
}
