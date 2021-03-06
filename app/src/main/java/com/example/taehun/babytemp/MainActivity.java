package com.example.taehun.babytemp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.taehun.babytemp.database.DBManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, UserDataFragment.OnHeadlineSelectedListener {

    NavigationView navigationView;
    DBManager mdbManager;


    FragmentTransaction ft;
    UserDataFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdbManager = DBManager.getInstance();
        mdbManager.openDB(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                intent.putExtra("isAddMode",true);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        makeNaviItems();



        ft= getSupportFragmentManager().beginTransaction();
        currentFragment = new UserDataFragment();
        ft.replace(R.id.placeholder, currentFragment);
        ft.commit();

    }

    private void makeNaviItems() {
        final Menu menu = navigationView.getMenu();
        Cursor cursor = mdbManager.getBabyInfo();

        if(cursor !=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){

                menu.add(0,1,0,cursor.getString(cursor.getColumnIndex("name")));//컬럼명의 대소문자를 가린다.

                cursor.moveToNext();
            }
            Log.i("","tempValue : "+"ValuesAAAAA");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
            intent.putExtra("isAddMode",true);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.e("", "item.getTitle() : " + item.getTitle());
        Intent i = new Intent("custom-event-name");

        i.putExtra("userId", mdbManager.getName2Id(item.getTitle().toString()));
        i.putExtra("userName", item.getTitle());
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(i);

        Toast.makeText(MainActivity.this ,mdbManager.getName2Id(item.getTitle().toString()),Toast.LENGTH_SHORT).show();;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onArticleSelected(int position) {

    }

    public void setActionBarTitle(String title){
        setTitle(title);
    }
}
