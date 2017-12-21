package com.smartcity.qiuchenly.Presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.smartcity.qiuchenly.Base.SQ_userManageList;
import com.smartcity.qiuchenly.DataModel.userLoginCallBackModel;
import com.smartcity.qiuchenly.Net.LoginAPI;
import com.smartcity.qiuchenly.Net.iCallback;
import com.smartcity.qiuchenly.Net.iLoginAPI;

import java.util.List;

/**
 * Author: qiuchenly
 * Date   : 10/11/2017
 * Usage :
 * Lasted:2017 11 10
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 10 , on 20:31
 */

/**
 * 11.24 日 修改内容
 * 1.完善了登录权限管理
 * 2.增加部分虚拟后端判断
 */

public class loginPresenter implements iLoginAPI {

    iLoginAPI Login;
    Handler handler;

    public loginPresenter() {
        handler = new Handler(Looper.getMainLooper());
        Login = new LoginAPI();
    }

    @Override
    public void login(final String mUser, final String mPass, final iCallback.loginCallBack callBack) {
        Login.login(mUser, mPass, new iCallback.loginCallBack() {
            @Override
            public void loginSuccess(final String result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Gson g = new Gson();
                        userLoginCallBackModel user = g.fromJson(result, userLoginCallBackModel.class);
                        if (user != null && user.errNo.equals("200")) {
                            user.mUserName = mUser;
                            user.mPassWord = mPass;
                            callBack.loginSuccess(user);
                        } else {
                            callBack.loginFailed("错误代码:" + user.errNo + "|" + user.errReason);
                        }
                    }
                });
            }

            @Override
            public void loginSuccess(userLoginCallBackModel userInfo) {
                //无需实现此接口
            }

            @Override
            public void loginFailed(final String errReason) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.loginFailed("数据获取失败!");
                    }
                });
            }
        });
    }

    @Override
    public void getManageUser(final iCallback.getUserManageData getUserManageData) {
        new Thread() {
            @Override
            public void run() {
                Login.getManageUser(new iCallback.getUserManageData() {
                    @Override
                    public void getDataSuccess(final List<SQ_userManageList> data) {
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
