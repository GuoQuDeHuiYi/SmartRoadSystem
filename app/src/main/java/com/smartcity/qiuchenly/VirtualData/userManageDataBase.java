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


<<<<<<< HEAD
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
                "                },{\n" +
                "                    \"carID\": \"辽A10005\",\n" +
                "                    \"carMaster\": \"张三\",\n" +
                "                    \"totalMoney\": 100,\n" +
                "                    \"carTypePic\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A10006\",\n" +
                "                    \"carMaster\": \"李四\",\n" +
                "                    \"totalMoney\": 99,\n" +
                "                    \"carTypePic\": 2\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A10007\",\n" +
                "                    \"carMaster\": \"王五\",\n" +
                "                    \"totalMoney\": 103,\n" +
                "                    \"carTypePic\": 3\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A10008\",\n" +
                "                    \"carMaster\": \"赵六\",\n" +
                "                    \"totalMoney\": 1,\n" +
                "                    \"carTypePic\": 4\n" +
                "                },{\n" +
                "                    \"carID\": \"辽A10009\",\n" +
                "                    \"carMaster\": \"张三\",\n" +
                "                    \"totalMoney\": 100,\n" +
                "                    \"carTypePic\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100010\",\n" +
                "                    \"carMaster\": \"李四\",\n" +
                "                    \"totalMoney\": 99,\n" +
                "                    \"carTypePic\": 2\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100011\",\n" +
                "                    \"carMaster\": \"王五\",\n" +
                "                    \"totalMoney\": 103,\n" +
                "                    \"carTypePic\": 3\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100012\",\n" +
                "                    \"carMaster\": \"赵六\",\n" +
                "                    \"totalMoney\": 1,\n" +
                "                    \"carTypePic\": 4\n" +
                "                },{\n" +
                "                    \"carID\": \"辽A100013\",\n" +
                "                    \"carMaster\": \"张三\",\n" +
                "                    \"totalMoney\": 100,\n" +
                "                    \"carTypePic\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100014\",\n" +
                "                    \"carMaster\": \"李四\",\n" +
                "                    \"totalMoney\": 99,\n" +
                "                    \"carTypePic\": 2\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100015\",\n" +
                "                    \"carMaster\": \"王五\",\n" +
                "                    \"totalMoney\": 103,\n" +
                "                    \"carTypePic\": 3\n" +
                "                },\n" +
                "                {\n" +
                "                    \"carID\": \"辽A100016\",\n" +
                "                    \"carMaster\": \"赵六\",\n" +
                "                    \"totalMoney\": 1,\n" +
                "                    \"carTypePic\": 4\n" +
                "                }\n" +
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
        return "{\"loginState\":\"0\",\"errNo\":\"200\",\"errReason\":\"\",\"isVIP\":\"" + (VIPLevel ? "1" : "0") + "\"}";
    }
}
