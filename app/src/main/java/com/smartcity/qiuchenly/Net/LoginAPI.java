package com.smartcity.qiuchenly.Net;

import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.DataModel.userManageModel;
import com.smartcity.qiuchenly.VirtualData.userManageDataBase;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 20:32
 */

public class LoginAPI implements iLoginAPI {
  public void login(String user, String pass, iCallback.loginCallBack callBack) {

  }

  public void getManageUser(iCallback.getUserManageData getUserManageData) {
    String str = userManageDataBase.getUsers();
    userManageModel list= Utils.jsonToListA(str, userManageModel.class);
    if (System.currentTimeMillis() % 2 == 0 || true) {
      getUserManageData.getDataSuccess(list);
    } else {
      getUserManageData.getDataFailed("数据获取失败！当然是原谅它！");
    }

  }
}
