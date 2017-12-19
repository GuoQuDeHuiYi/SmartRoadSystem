package com.smartcity.qiuchenly.Net;

import com.smartcity.qiuchenly.Base.SQ_userManageList;
import com.smartcity.qiuchenly.Base.Utils;
import com.smartcity.qiuchenly.VirtualData.userManageDataBase;

import java.util.List;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 20:32
 */

public class LoginAPI implements iLoginAPI {
    public void login(final String user, final String pass, final iCallback.loginCallBack callBack) {
        //2017 11.23日增加登录逻辑
        new Thread() {
            @Override
            public void run() {
                String result = userManageDataBase.VirtualWebSiteLogin(user, pass);
                if (result != null) {
                    callBack.loginSuccess(result);
                } else {
                    callBack.loginFailed("数据获取失败!");
                }
            }
        }.start();
    }

    public void getManageUser(iCallback.getUserManageData getUserManageData) {
        String str = userManageDataBase.getUsers();
        List<SQ_userManageList> user = Utils.dataBaseHelper.mUser_getAll();
//        userManageModel list = Utils.jsonToListA(str, userManageModel.class);
        if (System.currentTimeMillis() % 2 == 0 || true) {
            getUserManageData.getDataSuccess(user);
        } else {
            getUserManageData.getDataFailed("数据获取失败！当然是原谅它！");
        }
    }
}
