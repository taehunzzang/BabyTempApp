package com.example.taehun.babytemp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by taehun on 15. 11. 17..
 */
public class DBManager  {
    SQLiteDatabase mDB;
    DbHelper dbHelper;

    private static DBManager singleIntance = new DBManager();
    private DBManager(){
    }
    public static DBManager getInstance(){
        return singleIntance;
    }
    public void openDB(Context context){
        dbHelper = new DbHelper(context);
        mDB = dbHelper.getWritableDatabase();
    }
    public long insertValues(String name, String date){
        ContentValues dbValue = new ContentValues();
        dbValue.put("NAME",name);
        dbValue.put("BIRTHDAY",date);
        return mDB.insert(DbHelper.DB_TABLE_NAME,null,dbValue);
    }
    public boolean moreThen1(){
//        String [] FIELDS ={"_id"};
        Cursor cursor = mDB.query(DbHelper.DB_TABLE_NAME,null,null,null,null,null,null);
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


    public Cursor getBabyInfo() {
        Cursor cursor = mDB.query(DbHelper.DB_TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public long insertTempValue(String id, String temp){
        ContentValues dbValue = new ContentValues();
        dbValue.put("id",id);
        dbValue.put("temp",temp);
        return mDB.insert(DbHelper.DB_TABLE_NAME,null,dbValue);
    }
}
