package com.smartcity.qiuchenly.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qiuchenly on 2017/12/7.
 */

public class mDateBaseHelper extends SQLiteOpenHelper {
    private final String DATABASE_NAME = "mDataBase.db";
    private final String CREATE_TABLES = "create table if not " +
            "exists User(_id INTEGER PRIMARY KEY AUTOINCREMENT " +
            "NOT NULL,name TEXT not null,phone TEXT not null);";
    private final int DATABASE_VERSION = 1;

    public mDateBaseHelper(Context context, String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
