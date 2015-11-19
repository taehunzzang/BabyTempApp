package com.example.taehun.babytemp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.taehun.babytemp.database.DBManager;

/**
 * Created by taehun on 15. 11. 18..
 */
public class UserDataFragment extends Fragment implements View.OnClickListener{
    DBManager mdbManager;


    Button plus, minus, saveTemperature, reFreshData;
    TextView tempTxt, listTemperature;
    int tempValue = 365;


    public UserDataFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main,container,false);

        initUI(view);
        return view;
    }

    private void initUI(View view) {
        plus = (Button) view.findViewById(R.id.plus);
        minus = (Button) view.findViewById(R.id.minus);
        saveTemperature = (Button) view.findViewById(R.id.saveTemperature);
        reFreshData = (Button) view.findViewById(R.id.reFreshData);
        tempTxt = (TextView) view.findViewById(R.id.tempTxt);
        listTemperature = (TextView) view.findViewById(R.id.listTemperature);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        saveTemperature.setOnClickListener(this);
        reFreshData.setOnClickListener(this);


        tempTxt.setText(converValue(tempValue));

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.minus:
                tempValue = tempValue -1;
                tempTxt.setText(converValue(tempValue)+"");
                break;
            case R.id.plus:
                tempValue = tempValue + 1;
                tempTxt.setText(converValue(tempValue)+"");
                break;
            case R.id.saveTemperature:
                saveBabyTemperature();
                break;
            case R.id.reFreshData:
                showTemperatureData();
                break;
        }
    }

    private void showTemperatureData() {
        Log.e("Temperature",""+mdbManager.getBabaTemperaturData());
        listTemperature.setText(mdbManager.getBabaTemperaturData());
    }

    private void saveBabyTemperature() {
        mdbManager.insertTempValue("1", tempTxt.getText().toString());
        showTemperatureData();


    }

    private String converValue(int tempValue) {
        float tempValue1 = ((tempValue % 10)*0.1F)+(tempValue/10);
        return tempValue1+"";
    }
}
