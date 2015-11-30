package com.example.taehun.babytemp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehun.babytemp.database.DBManager;

import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{


    EditText babyName;
    TextView birthDay;
    Button saveData;
    DBManager mdbManager;
    boolean isAddMode = false;

    int year1 =0,month = 0 ,day = 0;
    static final int DIALOG_ID = 0;

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, new CustomDatePickerListener()/*dpickerListener*/, year1, month, day);
        return null;
    }

    class CustomDatePickerListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthDay.setText(year+" - "+(monthOfYear+1)+" - "+dayOfMonth);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        isAddMode = getIntent().getBooleanExtra("isAddMode", false);

        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getApplicationContext());
        if(mdbManager.moreThen1()&& !isAddMode){
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
        birthDay = (TextView) findViewById(R.id.birthDay);
        birthDay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveData:
                String tempMsg = validationRegisterUser();
                if(tempMsg.length()==0){

                    mdbManager.openDB(getApplicationContext());
                    mdbManager.insertValues(babyName.getText().toString(), birthDay.getText().toString());
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),tempMsg,Toast.LENGTH_SHORT).show();
                }

            break;
            case R.id.birthDay:
                final Calendar mCal = Calendar.getInstance();
                year1 = mCal.get(Calendar.YEAR);
                month = mCal.get(Calendar.MONTH);
                day = mCal.get(Calendar.DAY_OF_MONTH);
                showDialog(DIALOG_ID);
                break;
        }
    }

    private String validationRegisterUser(){//0 no Error
        String returnValue = "";
        if(babyName.getText().toString().length()==0){
            return returnValue = "이름을 입력하세요.";
        }
        if(birthDay.getText().toString().length()<4){
            return returnValue = "생년월일을 입력하세요";
        }

        return "";
    }

}
