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
        if(singleIntance!=null){

        }
        return singleIntance;
    }
    public void openDB(Context context){
        dbHelper = new DbHelper(context);
        mDB = dbHelper.getWritableDatabase();
    }
    public long insertValues(String name, String date){
        ContentValues dbValue = new ContentValues();
        dbValue.put("name",name);
        dbValue.put("birthday_date",date);
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

    public long insertTempValue(String id, String temp, long insertTime){
        ContentValues dbValue = new ContentValues();
        dbValue.put("user_id",id);
        dbValue.put("temperature",temp);
        dbValue.put("insertDate",insertTime);
        return mDB.insert(DbHelper.DB_TABLE_TEMPERATURE,null,dbValue);
    }

    public String getBabaTemperaturData() {
        Cursor cursor = mDB.query(DbHelper.DB_TABLE_TEMPERATURE,null,null,null,null,null,null);
        StringBuilder tempStr = new StringBuilder();

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                tempStr.append("userId : "+cursor.getInt(1)+" Temperature : "+cursor.getString(2)+"\n");
            }
        }
        return tempStr.toString();
    }

    public Cursor getBabyTemperatureData(){
        return mDB.query(DbHelper.DB_TABLE_TEMPERATURE,null,null,null,null,null,null);
    }
}
