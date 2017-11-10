package com.smartcity.qiuchenly.Base;

/**
 * Author: qiuchenly
 * Date  : 09/11/2017
 * Usage :
 * Lasted:2017 11 09
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 09 , on 23:58
 */

public class Utils {
  public static boolean isTwiceOpen() {
    ShareUtils.getSharePreferences(SharedContext.getContext());
    boolean is = ShareUtils.getBoolean("twiceOpen");
    if (!is) ShareUtils.put("twiceOpen", true);
    return is;
  }
}
