package com.example.taehun.babytemp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taehun on 15. 11. 15..
 */
public class DbHelper extends SQLiteOpenHelper {
    private static int DB_VERSION_INIT=1;
    public static String DB_NAME="BabyDB";
    public static String DB_TABLE_NAME="babyInfo";
    public static String DB_TABLE_TEMPERATURE="babyTemperature";


    //primary key autoincrement

    String babyTable = "CREATE TABLE "+DB_TABLE_NAME+" (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT, " +
            "BIRTHDAY DATE, " +
            "MEMO TEXT);";

    String babyTemperature = "CREATE TABLE "+DB_TABLE_TEMPERATURE+" (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "USER_ID INTEGER, " +
            "TEMPERATURE TEXT" +
            ");";
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION_INIT);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(babyTable);
            db.execSQL(babyTemperature);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
