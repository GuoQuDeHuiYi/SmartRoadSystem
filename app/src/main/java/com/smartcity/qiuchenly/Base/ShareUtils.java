package com.smartcity.qiuchenly.Base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: QiuChenly
 * Date   : 2017/10/14
 * Usage :
 * Lasted:2017 10 14
 * ProjectName:SmartRoadSystem
 * Create: 2017 10 14 , on 16:35
 */

public class ShareUtils {

  //使用设计思想: 单例模式
  static SharedPreferences share = null;


  /**
   * Context 参数传进来的意义
   * 不同的Activity是不会共享一个Preferences的,需要传一个指定的上下文 来统一调用
   *
   * @return
   */
  public static SharedPreferences getSharePreferences() {
    if (share == null)
      share = SharedContext.getContext()
              .getSharedPreferences("QiuChenPreferences",
                      Context.MODE_PRIVATE
              );
    return share;
  }

  public static <T> void put(String key, T data) {
    SharedPreferences.Editor edit = share.edit();
    edit.putString(key, data.toString());
    edit.apply();
    edit.commit();
  }

  public static void put(String key, boolean data) {
    SharedPreferences.Editor edit = share.edit();
    edit.putBoolean(key, data);
    edit.apply();
    edit.commit();
  }

  public static void put(String key, int value) {
    SharedPreferences.Editor edit = share.edit();
    edit.putInt(key, value);
    edit.apply();
    edit.commit();
  }

  public static void put(String key, float value) {
    SharedPreferences.Editor edit = share.edit();
    edit.putFloat(key, value);
    edit.apply();
    edit.commit();
  }


  /**
   * 读取保存的Float数据,成功返回非-1,失败返回-1
   *
   * @param key 字符串key
   * @return
   */
  public static float getF(String key) {
    return share.getFloat(key, -1);
  }


  public static int getInt(String key) {
    return share.getInt(key, -1);//成功返回数据,失败返回-1
  }

  public static String get(String key) {
    return share.getString(key, "");
  }

  public static boolean getBoolean(String key) {
    return share.getBoolean(key, false);
  }
}
