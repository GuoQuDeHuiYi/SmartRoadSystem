package com.smartcity.qiuchenly.VirtualData;

/**
 * Author: qiuchenly
 * Date  : 09/11/2017
 * Usage :虚拟数据层，暂时无后端，用此层数据代替
 * Lasted:2017 11 09
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 09 , on 23:09
 */

public class userManageDataBase {
  public userManageDataBase() {

  }

  public static String getUsers() {
    return "{\n" +
            "            \"data\": [\n" +
            "                {\n" +
            "                    \"carID\": \"辽A10001\",\n" +
            "                    \"carMaster\": \"张三\",\n" +
            "                    \"totalMoney\": 100,\n" +
            "                    \"carTypePic\": 1\n" +
            "                },\n" +
            "                {\n" +
            "                    \"carID\": \"辽A10002\",\n" +
            "                    \"carMaster\": \"李四\",\n" +
            "                    \"totalMoney\": 99,\n" +
            "                    \"carTypePic\": 2\n" +
            "                },\n" +
            "                {\n" +
            "                    \"carID\": \"辽A10003\",\n" +
            "                    \"carMaster\": \"王五\",\n" +
            "                    \"totalMoney\": 103,\n" +
            "                    \"carTypePic\": 3\n" +
            "                },\n" +
            "                {\n" +
            "                    \"carID\": \"辽A10004\",\n" +
            "                    \"carMaster\": \"赵六\",\n" +
            "                    \"totalMoney\": 1,\n" +
            "                    \"carTypePic\": 4\n" +
            "                }" +
            "            ]\n" +
            "        }";
  }

  public static String VirtualWebSiteLogin(String user, String pass) {
    //defined five users by default
    String users[] = new String[]{
            "user1", "user2", "user3", "user4", "user5"
    };
    //defined default userPass
    String defaultPass = "123456";

    boolean isHave = false;
    int count = 0;
    //start verify user is have?
    for (String user_Temp : users) {
      count++;
      if (user_Temp.equals(user)) {
        isHave = true;//userName is valid!
        break;
      }
    }
    if (!isHave) {
      return "{\"loginState\":\"-1\",\"errNo\":\"1000\",\"errReason\":\"userName is unValid!\"}";
    }
    //if passWord is Error.
    if (!defaultPass.equals(pass)) {
      return "{\"loginState\":\"-1\",\"errNo\":\"1001\",\"errReason\":\"passWord is error!\"}";
    }
    //now,this account is valid,so we now must return this account Relevant Information
    boolean VIPLevel = false;
    if (count == 1) {
      VIPLevel = true;
    }
    //sex 1 = 男 2 = 女
    return "{\"loginState\":\"0\",\"cardID\":\"身份证号：3209241999999999\",\"sex\":\"性别：1\"," +
            "\"errNo\":\"200\"," + "\"userName\":\"" + users[count - 1] + "\"," +
            "\"errReason\":\"\"," + "\"phoneNum\":\"电话号码：18999999999\"," +
            "\"regTime\":\"注册日期2017.5.1\"," +
            "\"isVIP\":\"" + (VIPLevel ? "1" : "0") + "\"}";
  }
}
