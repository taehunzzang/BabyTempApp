package com.example.taehun.babytemp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehun.babytemp.database.DBManager;

/**
 * Created by taehun on 15. 11. 18..
 */
public class UserDataFragment extends Fragment implements View.OnClickListener{

    public OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener l){
        mCallback = l;
    }
    DBManager mdbManager;
    String currentUserId = "";

    Button plus, plusMore, minusMore, minus, saveTemperature, reFreshData;
    TextView tempTxt, listTemperature;
    int tempValue = 365;
    ListView listViewData;
    UserDataCursorAdapter adapter;


    public UserDataFragment() {
        super();

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);
        mCallback.onArticleSelected(1);
        initUI(view);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
        return view;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentUserId = intent.getStringExtra("userId");
            getActivity().setTitle(intent.getStringExtra("userName"));

            changeUser(currentUserId);
//            String message = intent.getStringExtra("message");
//            Log.d("receiver", "Got message: " + message);
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void initUI(View view) {

        plus = (Button) view.findViewById(R.id.plus);
        minus = (Button) view.findViewById(R.id.minus);
        plusMore = (Button) view.findViewById(R.id.plusMore);
        minusMore = (Button) view.findViewById(R.id.minusMore);
        saveTemperature = (Button) view.findViewById(R.id.saveTemperature);
        reFreshData = (Button) view.findViewById(R.id.reFreshData);
        tempTxt = (TextView) view.findViewById(R.id.tempTxt);
        listTemperature = (TextView) view.findViewById(R.id.listTemperature);
        listViewData = (ListView) view.findViewById(R.id.listViewData);

        plus.setOnClickListener(this);
        plusMore.setOnClickListener(this);
        minusMore.setOnClickListener(this);
        minus.setOnClickListener(this);
        saveTemperature.setOnClickListener(this);
        reFreshData.setOnClickListener(this);

        tempTxt.setText(converValue(tempValue));

//        listViewData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"list Touch",Toast.LENGTH_SHORT).show();
//            }
//        });

        listViewData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = (Cursor) adapterView.getItemAtPosition(i);
                mdbManager.deleteItem(c.getInt(0));
                showTemperatureData();
                Toast.makeText(getActivity(),"데이터가 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }

        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getActivity());
        currentUserId = mdbManager.getOldUser();
        getActivity().setTitle(mdbManager.getOldUserName());
        adapter = new UserDataCursorAdapter(getActivity(),mdbManager.getBabyTemperatureData(currentUserId),true);
        listViewData.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.minus:
                tempValue = tempValue - 1;
                tempTxt.setText(converValue(tempValue)+"");
                break;
            case R.id.minusMore:
                tempValue = tempValue - 10;
                tempTxt.setText(converValue(tempValue)+"");
                break;
            case R.id.plus:
                tempValue = tempValue + 1;
                tempTxt.setText(converValue(tempValue)+"");
                break;
            case R.id.plusMore:
                tempValue = tempValue + 10;
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
        Log.e("Temperature", "" + mdbManager.getBabaTemperaturData());
        listTemperature.setText(mdbManager.getBabaTemperaturData());
        adapter.swapCursor(mdbManager.getBabyTemperatureData(currentUserId));
    }

    private void saveBabyTemperature() {
        mdbManager.insertTempValue(currentUserId, tempTxt.getText().toString(), System.currentTimeMillis());
        showTemperatureData();
    }

    private String converValue(int tempValue) {
        float tempValue1 = ((tempValue % 10)*0.1F)+(tempValue/10);
        return tempValue1+"";
    }

    private void changeUser(String id){
        adapter.swapCursor(mdbManager.getBabyTemperatureData(id));

    }


}
