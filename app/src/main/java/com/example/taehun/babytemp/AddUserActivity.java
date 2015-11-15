package com.example.taehun.babytemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{


    Button saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initUI();
    }

    private void initUI() {
        saveData = (Button) findViewById(R.id.saveData);
        saveData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveData:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            break;
        }

    }
}
