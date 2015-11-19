package com.example.taehun.babytemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taehun.babytemp.database.DBManager;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{


    EditText babyName,year, month, day;
    Button saveData;
    DBManager mdbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getApplicationContext());
        if(mdbManager.moreThen1()){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        initUI();
    }

    private void initUI() {
        saveData = (Button) findViewById(R.id.saveData);
        saveData.setOnClickListener(this);

        babyName = (EditText) findViewById(R.id.babyName);
        year = (EditText) findViewById(R.id.year);
        month = (EditText) findViewById(R.id.month);
        day = (EditText) findViewById(R.id.day);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveData:
                String tempMsg = validationRegisterUser();
                if(tempMsg.length()==0){

                    mdbManager.openDB(getApplicationContext());
                    mdbManager.insertValues(babyName.getText().toString(), year.getText().toString()+month.getText().toString()+day.getText().toString());
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),tempMsg,Toast.LENGTH_SHORT).show();
                }

            break;
        }
    }

    private String validationRegisterUser(){//0 no Error
        String returnValue = "";
        if(babyName.getText().toString().length()==0){
            return returnValue = "이름을 입력하세요.";
        }
        if(year.getText().toString().length()<4){
            return returnValue = "년도를 정확히 입력하세요";
        }
        if(month.getText().toString().length()==0){
            return returnValue = "월을 정확히 입력하세요";
        }
        if(day.getText().toString().length()==0){
            return returnValue = "날을 정확히 입력하세요";
        }
        return "";
    }
}
