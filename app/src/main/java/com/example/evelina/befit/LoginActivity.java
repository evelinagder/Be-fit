package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;
;
import com.example.evelina.befit.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import com.facebook.ProfileTracker;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_REG_USER = 10;

    Button login;
    Button register;
    EditText username;
    EditText password;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ProfileTracker mProfileTracker;
    NetworkStateChangedReceiver receiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.button_LoginL);
        register = (Button) findViewById(R.id.buttonRegisterL);
        username = (EditText) findViewById(R.id.editText_emailL);
        password = (EditText) findViewById(R.id.editText_passwordL);
        loginButton = (LoginButton) findViewById(R.id.login_button_has_account);
        callbackManager = CallbackManager.Factory.create();
        receiver = new NetworkStateChangedReceiver();
        //TODO see if we can get the person's email
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();

                if (usernameString.isEmpty()) {
                    username.setError("Email is compulsory");
                    username.requestFocus();
                    return;
                }
                if (passwordString.length() == 0) {
                    password.setError("Password is compulsory");
                    password.requestFocus();
                    return;
                }
                if (!DbManager.getInstance(LoginActivity.this).validateLogin(usernameString, passwordString)) {
                    username.setError("Invalid credentials");
                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                    return;
                }

                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("currentUser", usernameString);
                editor.commit();
                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, TabbedActivity.class);
                // intent.putExtra("loggedUser", user); TODO passing user from activity 1 to act 2!
                intent.putExtra("username", username.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                username.setError(null);
                startActivityForResult(intent, REQUEST_REG_USER);
            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,TabbedActivity.class);
                if(Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();
                        }
                    };
                }
                else {
                    Profile profile = Profile.getCurrentProfile();

                    Log.e("facebook", profile.getFirstName());

                    String password = profile.getId();
                    String username = profile.getFirstName() + " " + profile.getLastName() + password;
                    Bundle params = new Bundle();
                    params.putString("fields","email,gender");
                    final User user = new User(username,password,"none","",0,0,0);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "me",params,HttpMethod.GET, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            if(response!=null){
                                try {
                                    String email = response.getJSONObject().getString("email");
                                    Log.e("TAG",email);
                                    String gender = response.getJSONObject().getString("gender");
                                    Log.e("TAG",gender);
                                    user.setEmail(email);
                                    user.setGender(gender);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).executeAsync();

                    DbManager.getInstance(LoginActivity.this).addUser(user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),0,0,0);
                    SharedPreferences prefs = LoginActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logged_in", true);
                    editor.putString("currentUser",username);
                    editor.commit();
                    intent.putExtra("username",profile.getFirstName() + " " + profile.getLastName()+password);
                    intent.putExtra("id",profile.getId());
                }

                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.e("TAG", "user pressed cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("TAG", error.getMessage());
                Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REG_USER) {
            if (resultCode == RegisterActivity.RESULT_REG_SUCCESSFUL) {
                String emailT = data.getStringExtra("username");
                String pass = data.getStringExtra("password");
                username.setText(emailT);
                password.setText(pass);
            }
        }
        //TODO here if user logged with fb
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);

        super.onDestroy();
    }


}

