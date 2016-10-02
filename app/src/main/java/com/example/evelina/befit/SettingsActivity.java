package com.example.evelina.befit;

import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SettingsAdapter mSettingsAdapter;
    private Toolbar toolbar;
    private  NetworkStateChangedReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mRecyclerView = (RecyclerView) findViewById(R.id.settings_recycler_view);
        toolbar = (Toolbar) findViewById(R.id.app_bar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> settingsList= new ArrayList<>();
        settingsList.add("email");
        settingsList.add("gender");
        settingsList.add("height");
        settingsList.add("weight");
        //probably alarm should be here
        settingsList.add("notifications & alarms");
        mSettingsAdapter = new SettingsAdapter(this,settingsList);
        mRecyclerView.setAdapter(mSettingsAdapter);
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void showFragment() {
        TimePickerNotificationFragment fragment = new TimePickerNotificationFragment();
       fragment.show(getSupportFragmentManager(),"time");

    }
}
