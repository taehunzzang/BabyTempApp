package com.example.taehun.babytemp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by taehun on 2015-11-20.
 */
public class UserDataCursorAdapter extends CursorAdapter {
    SimpleDateFormat sdf;
    public UserDataCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.user_data_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView userData = (TextView) view.findViewById(R.id.userData);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(cursor.getLong(3));
        userData.setText(cursor.getString(2)+sdf.format(cal.getTimeInMillis()));
        changeTextColor(userData, cursor.getString(2));
    }

    private void changeTextColor(TextView userData, String string) {
        float tempValue = convertStr2long(string);
        if(tempValue<37.0){
            userData.setTextColor(Color.parseColor("#ffbb00"));
        }else if(tempValue<38.0){
            userData.setTextColor(Color.parseColor("#bbff00"));
        }else{
            userData.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    private float convertStr2long(String string) {
        float tempValue=0;
        try{
            tempValue = Float.parseFloat(string);
        }catch (Exception e){
            return 0;
        }
        return tempValue;
    }


}
