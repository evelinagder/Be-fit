package com.example.evelina.befit;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.User;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SettingsAdapter mSettingsAdapter;
    private Toolbar toolbar;
    private ImageButton profilePic;
    private  NetworkStateChangedReceiver receiver;
    private static final int REQUEST_CODE_GALLERY=7;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        username=getIntent().getStringExtra("loggedUser");

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
        profilePic= (ImageButton)findViewById(R.id.picture_profile_change);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_GALLERY && data != null){
            Uri image= data.getData();
            profilePic.setImageURI(image);
            DbManager.getInstance(SettingsActivity.this).updateProfilePicture(username,image);
        }
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
