package com.smartcity.qiuchenly.Presenter;

import android.os.Handler;
import android.os.Looper;

import com.smartcity.qiuchenly.DataModel.userManageModel;
import com.smartcity.qiuchenly.Net.Callback;
import com.smartcity.qiuchenly.Net.ILoginAPI;
import com.smartcity.qiuchenly.Net.LoginAPI;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 20:31
 */

public class loginPresenter implements ILoginAPI {

  LoginAPI Login;
  Handler handler;

  public loginPresenter() {
    handler = new Handler(Looper.getMainLooper());
    Login = new LoginAPI();
  }

  @Override
  public void login(String user, String pass, Callback.loginCallBack callBack) {

  }

  @Override
  public void getManageUser(final Callback.getUserManageData getUserManageData) {
    new Thread() {
      @Override
      public void run() {
        Login.getManageUser(new Callback.getUserManageData() {
          @Override
          public void getDataSuccess(final userManageModel data) {
            handler.post(new Runnable() {
              @Override
              public void run() {
                getUserManageData.getDataSuccess(data);
              }
            });

          }

          @Override
          public void getDataFailed(final String errReason) {
            handler.post(new Runnable() {
              @Override
              public void run() {
                getUserManageData.getDataFailed(errReason);
              }
            });
          }
        });
      }
    }.start();
  }
}
