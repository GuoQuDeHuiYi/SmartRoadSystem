package com.smartcity.qiuchenly.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartcity.qiuchenly.Base.SQLCreator;
import com.smartcity.qiuchenly.Base.SQ_PayHistoryCursor;
import com.smartcity.qiuchenly.Base.SQ_userManageList;
import com.smartcity.qiuchenly.Base.Utils_K;
import com.smartcity.qiuchenly.DataModel.userCars;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuchenly on 2017/12/7.
 */

public class mDataBaseHelper extends SQLiteOpenHelper {

    private final String CREATE_TABLES = "create table if not " +
            "exists User(_id INTEGER PRIMARY KEY AUTOINCREMENT " +
            "NOT NULL,name TEXT not null,phone TEXT not null,pass TEXT not null);";

    private mDataBaseHelper mDB;

    public mDataBaseHelper(Context context, String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
        mDB = this;

        mUserCarHelper = new userCarHelper();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    String PAY_HISTORY_NAME = "PayHistory";
    String USER_MANAGE_NAME = "UserManage";


    /**
     * 创建用户管理页
     */
    public void mCreateUserManageTabel() {
        String CREATE_TABLE = "create table if not exists " + USER_MANAGE_NAME + " (" +
                "id integer primary key autoincrement,user_carPic integer not null,user_carID TEXT not null," +
                "user_name TEXT not null,user_totalMoney integer not null" +
                ");";
        this.getWritableDatabase().execSQL(CREATE_TABLE);
    }


    /**
     * 创建支付历史的数据库表
     * 字段：id,carID,pay,totalMoney,whoPay,payTime
     */
    public void mCreateNewPayHistoryTable() {
        String CREATE_PAY_HISTORY_TABLE = "create table if not exists " + PAY_HISTORY_NAME
                + "(id integer primary key autoincrement,carID TEXT not null,pay integer not null," +
                "totalMoney integer not null,whoPay TEXT not null,payTime TEXT not null);";
        mDB.getWritableDatabase().execSQL(CREATE_PAY_HISTORY_TABLE);
    }


    /**
     * 向数据库中插入用户的信息
     *
     * @param resID           头像ID
     * @param user_carID      车辆id
     * @param user_name       车主
     * @param user_totalMoney 用户余额
     */
    public void mUser_insert(int resID,
                             String user_carID,
                             String user_name,
                             int user_totalMoney) {
        String insert = "insert into " + USER_MANAGE_NAME +
                "(user_carPic,user_carID,user_name,user_totalMoney)" +
                "values(" + resID + ",'" + user_carID + "','" + user_name + "'," + user_totalMoney +
                ");";
        Execute(insert);
    }

    public void mUpdateTotalMoney(String carID, int total) {
        String updata = "update " + USER_MANAGE_NAME +
                " set user_totalMoney = " + total + " where user_carID = '" + carID + "'";
        Execute(updata);
    }


    /**
     * 获取数据库中所有用户的详细信息
     *
     * @return 用户信息列表
     */
    public List<SQ_userManageList> mUser_getAll() {
        List<SQ_userManageList> users = new ArrayList<>();
        String get = "select * from " + USER_MANAGE_NAME;
        Cursor cursor = this.getReadableDatabase().rawQuery(get, null);
        if (cursor.moveToFirst()) {
            int ids = 1;
            for (; ; cursor.moveToNext()) {
                SQ_userManageList user = new SQ_userManageList();
                user.id = ids;
                ids++;
                user.user_carID = cursor.getString(2);
                user.user_carPic = cursor.getInt(1);
                user.user_name = cursor.getString(3);
                user.user_totalMoney = cursor.getInt(4);
                users.add(user);
                if (cursor.isLast()) {
                    break;
                }
            }
        }
        return users;
    }

    /**
     * 根据车牌号查询单条数据
     *
     * @param carID 车牌号
     * @return 车辆余额
     */
    public int mGetBalanceByCarID(String carID) {
        String get = "select user_totalMoney from "
                + USER_MANAGE_NAME + " where user_carID = '" + carID + "';";
        Cursor c = this.getReadableDatabase().rawQuery(get, null);
        if (c.moveToFirst()) {
            int a = c.getInt(0);
            return a;
        }
        return 0;
    }

    public void mPay_History_InsertItems(String carID, int pay, int totalMoney, String who) {
        String insert = "insert into " + PAY_HISTORY_NAME + "(carID,pay,totalMoney,whoPay,payTime)" +
                "values('" + carID + "'," + pay + "," + totalMoney + ",'" + who + "'," + "'" + Utils_K
                .Companion.getNowDate() + "'" +
                ");";
        mDB.Execute(insert);
    }


    private static final String TAG = "mDataBaseHelper";

    public List<SQ_PayHistoryCursor> mQueryPayHistory() {
        String get = String.format("select * from %s", PAY_HISTORY_NAME);
        List<SQ_PayHistoryCursor> mList = new ArrayList<>();
        Cursor cursor = mDB.getReadableDatabase().rawQuery(get, null);

        if (cursor.moveToFirst()) {
            for (; ; cursor.moveToNext()) {
                SQ_PayHistoryCursor c = new SQ_PayHistoryCursor();
                c.id = cursor.getInt(0);
                c.carID = cursor.getString(1);
                c.pay = cursor.getInt(2);
                c.totalMoney = cursor.getInt(3);
                c.whoPay = cursor.getString(4);
                c.payTime = cursor.getString(5);
                mList.add(c);
                if (cursor.isLast()) {
                    break;
                }
            }
            cursor.close();
        }
        return mList;
    }

    void Execute(String exec) {
        getWritableDatabase().execSQL(exec);
    }

    public userCarHelper mUserCarHelper;

    public class userCarHelper {
        public userCarHelper() {
        }

        String mTableName = "userCars";

        public void createTable() {
            //carPic: Int, carName: String, carUserName
            String exec = new SQLCreator.CreateTableBuilder(mTableName)
                    .setField("carPic", "integer")
                    .setField("carName", "TEXT")
                    .setField("carUserName", "TEXT")
                    .Build_Text();
            mDB.Execute(exec);
        }

        public void insert(String carPic, String carName, String carUserName) {
            String exec = new SQLCreator.InsertItemBuilder(mTableName)
                    .addAttribute("carPic", carPic)
                    .addAttribute("carName", "'" + carName + "'")
                    .addAttribute("carUserName", "'" + carUserName + "'")
                    .Build().toString();
            mDB.Execute(exec);
        }

        public void delete() {

        }

        public List<userCars> getAll() {
            return new ArrayList<>();
        }

        public List<userCars> getBy(String carUserName) {
            String exec = new SQLCreator.GetItemsBuilder(mTableName)
                    .AddAttribute("carUserName", "'" + carUserName + "'")
                    .Build().toString();
            List<userCars> list = new ArrayList<>();
            Cursor cursor = getReadableDatabase().rawQuery(exec, null);
            if (cursor.moveToFirst()) {
                do {
                    userCars userCars = new userCars(
                            cursor.getInt(3), cursor.getString(2), cursor.getString(1)
                    );
                    list.add(userCars);
                    if (cursor.isLast()) break;
                }
                while (cursor.moveToNext());
            }
            return list;
        }
    }
}
