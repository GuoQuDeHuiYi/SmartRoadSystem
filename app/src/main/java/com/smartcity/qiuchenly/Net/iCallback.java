package com.smartcity.qiuchenly.Net;

import com.smartcity.qiuchenly.DataModel.userLoginCallBackModel;
import com.smartcity.qiuchenly.DataModel.userManageModel;

import java.util.List;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 20:33
 */

public interface iCallback {
  interface loginCallBack {
    void loginSuccess(final String result);

    void loginSuccess(final userLoginCallBackModel userInfo);

    void loginFailed(final String errReason);
  }

  interface getUserManageData {
    void getDataSuccess(userManageModel data);

    void getDataFailed(String errReason);
  }
}
