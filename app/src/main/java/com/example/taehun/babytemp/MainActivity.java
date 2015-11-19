package com.example.taehun.babytemp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehun.babytemp.database.DBManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    NavigationView navigationView;
    DBManager mdbManager;


    Button plus, minus, saveTemp;
    TextView tempTxt;
    int tempValue = 365;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getApplicationContext());
        makeNaviItems();
        initUI();
    }

    private void initUI() {
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        saveTemp = (Button) findViewById(R.id.saveTemp);
        tempTxt = (TextView) findViewById(R.id.tempTxt);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        saveTemp.setOnClickListener(this);


        tempTxt.setText(converValue(tempValue));

    }

    private String converValue(int tempValue) {
        float tempValue1 = ((tempValue % 10)*0.1F)+(tempValue/10);
        return tempValue1+"";
    }

    private void makeNaviItems() {

        final Menu menu = navigationView.getMenu();
        Cursor cursor = mdbManager.getBabyInfo();

        if(cursor !=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){

                menu.add(0,1,0,cursor.getString(cursor.getColumnIndex("NAME")));//컬럼명의 대소문자를 가린다.

                cursor.moveToNext();
            }

            for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
                final View child = navigationView.getChildAt(i);
                if (child != null && child instanceof ListView) {
                    final ListView menuView = (ListView) child;
                    final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                    final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                    wrapped.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==1){
            Toast.makeText(getApplicationContext(),"taehun kim",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            case R.id.saveData:
                saveBabyTempurature();
                break;
        }

    }

    private void saveBabyTempurature() {
//        tempTxt

        mdbManager.getBabyInfo();
    }
}
