package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_REG_USER = 10;

    Button login;
    Button register;
    EditText email;
    EditText password;
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.button_LoginL);
        register = (Button) findViewById(R.id.buttonRegisterL);
        email = (EditText) findViewById(R.id.editText_emailL);
        password = (EditText) findViewById(R.id.editText_passwordL);
        loginButton= (LoginButton) findViewById(R.id.login_button_has_account);
        callbackManager = CallbackManager.Factory.create();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                if (emailString.isEmpty()) {
                    email.setError("Username is compulsory");
                    email.requestFocus();
                    return;
                }
                if (passwordString.length() == 0) {
                    password.setError("Password is compulsory");
                    password.requestFocus();
                    return;
                }
//                if (!UsersManager.getInstance(LoginActivity.this).validalteLogin(usernameString, passwordString)) {
//                    username.setError("Invalid credentials");
//                    username.setText("");
//                    password.setText("");
//                    username.requestFocus();
//                    return;
//                }  BAZA DANNI?

                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("currentUser",emailString);
                editor.commit();
                Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_LONG).show();

                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                 // intent.putExtra("loggedUser", user); TODO passing user from activity 1 to act 2!
                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);


            }
        });

          register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                email.setError(null);
                startActivityForResult(intent, REQUEST_REG_USER);
            }
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile =Profile.getCurrentProfile();
                AccessToken accessToken = loginResult.getAccessToken();
                //TODO passing intent with loggedUser
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REG_USER){
            if(resultCode == RegisterActivity.RESULT_REG_SUCCESSFUL){
                String emailT = data.getStringExtra("email");
                String pass = data.getStringExtra("password");
                email.setText(emailT);
                password.setText(pass);
            }
        }
        //TODO here if user logged with fb
        callbackManager.onActivityResult(requestCode,resultCode,data);

}
}
