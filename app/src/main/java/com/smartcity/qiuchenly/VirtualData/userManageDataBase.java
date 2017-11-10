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
  public userManageDataBase(){

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
            "                }\n" +
            "            ]\n" +
            "        }";
  }
}
