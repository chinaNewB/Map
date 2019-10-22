package com.example.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper
{
    private String CREATE_TABLE = "";
    private String tableName = "";

    public DBOpenHelper(Context context, String dbName, int dbVersion, String tableName, String sql)
    {
        super(context, dbName, null, dbVersion);
        CREATE_TABLE = sql;
        this.tableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oleVersion, int newVersion)
    {
        db.execSQL("drop table if  exists"+tableName);
        onCreate(db);
    }
}