package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.evelina.befit.adapters.SettingsAdapter;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SettingsAdapter mSettingsAdapter;
    private Toolbar toolbar;
    private CircleImageView profilePic;
    private NetworkStateChangedReceiver receiver;
    private static final int REQUEST_CODE_GALLERY = 7;
    private String username;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_settings);
        username = getIntent().getStringExtra("loggedUser");
        callbackManager = CallbackManager.Factory.create();
        mRecyclerView = (RecyclerView) findViewById(R.id.settings_recycler_view);
        toolbar = (Toolbar) findViewById(R.id.app_bar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, TabbedActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> settingsList = new ArrayList<>();
        settingsList.add("email");
        settingsList.add("gender");
        settingsList.add("height");
        settingsList.add("weight");
        settingsList.add("notifications & alarms");

        mSettingsAdapter = new SettingsAdapter(this, settingsList);
        mRecyclerView.setAdapter(mSettingsAdapter);

        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        profilePic = (CircleImageView) findViewById(R.id.profile_image);
        String sp = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("loggedWith", "none");
        if (sp.equals("registration")) {
            profilePic.setVisibility(View.VISIBLE);
        }
        final User user = DbManager.getInstance(this).getUser(username);
        if (sp.equals("facebook")) {
            Bundle params = new Bundle();
            params.putString("fields", "email,gender");
            new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    if (response != null) {
                        try {
                            String email = response.getJSONObject().getString("email");
                            Log.e("TAG", email);
                            String gender = response.getJSONObject().getString("gender");
                            Log.e("TAG", gender);
                            user.setEmail(email);
                            user.setGender(gender);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).executeAsync();
        }

        if (user.getProfilePic() != null) {
            profilePic.setImageURI(user.getProfilePic());
        }
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && data != null) {
            Uri image = data.getData();
            profilePic.setImageURI(image);
            DbManager.getInstance(SettingsActivity.this).updateProfilePicture(username, image);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void showFragment(Bundle bundle) {
        if (bundle != null) {
            TimePickerNotificationFragment fragment = new TimePickerNotificationFragment();
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(), "time");
        }
    }

    //TODO here
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this,TabbedActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
        finish();
    }
}
