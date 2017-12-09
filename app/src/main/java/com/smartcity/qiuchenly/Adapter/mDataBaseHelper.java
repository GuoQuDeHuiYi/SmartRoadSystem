package com.smartcity.qiuchenly.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.smartcity.qiuchenly.Base.SQ_PayHistoryCursor;

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
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    String PAY_HISTORY_NAME = "PayHistory";

    public void mCreatNewPayHistoryTable() {
        String CREATE_PAY_HISTORY_TABLE = "create table if not exists " + PAY_HISTORY_NAME
                + "(id integer primary key autoincrement,carID TEXT not null,pay integer not null," +
                "totalMoney integer not null,whoPay TEXT not null,payTime TEXT not null);";
        mDB.getWritableDatabase().execSQL(CREATE_PAY_HISTORY_TABLE);
    }

    public void mInsertItems(String carID, int pay, int totalMoney, String who) {
        String insert = "insert into " + PAY_HISTORY_NAME + "(carID,pay,totalMoney,whoPay,payTime)" +
                "values('" + carID + "'," + pay + "," + totalMoney + ",'" + who + "'," + "'times'" + ");";
        mDB.getWritableDatabase().execSQL(insert);
    }


    private static final String TAG = "mDataBaseHelper";

    public void mQueryPayHistory() {
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
                mList.add(c);
                if (cursor.isLast()) {
                    break;
                }
            }
            cursor.close();
        }
    }
}
