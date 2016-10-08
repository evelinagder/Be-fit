package com.example.evelina.befit;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    TextView heading;
    EditText usernameR;
    EditText passR;
    EditText emailR;
    EditText kilogramsET;
    EditText santimetersET;
    TextView heightTV;
    TextView weightTV;
    Button registerR;
    Spinner genderSpinner;
    public static final int RESULT_REG_SUCCESSFUL = 10;
    String gender;
    NetworkStateChangedReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        heading = (TextView) findViewById(R.id.create_account_heading);
        usernameR = (EditText) findViewById(R.id.editText_username);
        passR = (EditText) findViewById(R.id.editText_password);
        emailR = (EditText) findViewById(R.id.editText_email);
        registerR = (Button) findViewById(R.id.button_registerR);
        genderSpinner= (Spinner)findViewById(R.id.spinner_gender);
        kilogramsET= (EditText) findViewById(R.id.weight_kilograms);
        santimetersET= (EditText) findViewById(R.id.height_santimeters);
        heightTV = (TextView) findViewById(R.id.height_TV);
        weightTV = (TextView) findViewById(R.id.weight_TV);



        ArrayAdapter adapter1= ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(adapter1);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(RegisterActivity.this, "female", Toast.LENGTH_SHORT).show();
                        gender="female";
                        break;
                    case 1:
                        Toast.makeText(RegisterActivity.this, "male", Toast.LENGTH_SHORT).show();
                        gender="male";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }



        });

        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

            registerR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usernameU = usernameR.getText().toString().trim();
                    String passU = passR.getText().toString();
                    String emailU = emailR.getText().toString();
                    String kilogramsU=kilogramsET.getText().toString();
                    String santimetersU = santimetersET.getText().toString();
                    int kilograms=0;
                    int santimeters = 0;

                    String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
                    if(kilogramsU.isEmpty()){
                        kilogramsET.setError("Enter kilograms");
                        kilogramsET.requestFocus();
                        return;
                    }

                    if(santimetersU.isEmpty()){
                        santimetersET.setError("Enter height");
                        santimetersET.requestFocus();
                        return;

                    }
                    if (usernameU.isEmpty()) {
                        usernameR.setError("Username is compulsory");
                        usernameR.requestFocus();
                        return;
                    }
                    if (passU.length() == 0) {
                        passR.setError("Password is compulsory");
                        passR.requestFocus();
                        return;
                    }

                    if (emailU.isEmpty()) {
                        emailR.setError("Email is compulsory");
                        emailR.requestFocus();
                        return;
                    }

                    if (!emailU.matches(emailPattern)) {
                        emailR.setError("Invalid email");
                        emailR.requestFocus();
                        return;
                    }
                    if(!santimetersU.isEmpty()){
                        try{
                            santimeters = Integer.parseInt(santimetersU);
                        }catch (NumberFormatException e){
                            Log.e("TAG",e.getMessage());
                        }
                    }
                    if(!kilogramsU.isEmpty()){
                        try{
                            kilograms = Integer.parseInt(kilogramsU);
                        }catch (NumberFormatException e){
                            Log.e("TAG",e.getMessage());
                        }
                    }
                    if (DbManager.getInstance(RegisterActivity.this).existsUser(usernameU)) {
                        usernameR.setError("User already exists");
                        usernameR.setText("");
                        usernameR.requestFocus();
                        return;
                    }
                    DbManager.getInstance(RegisterActivity.this).addUser(usernameU, passU, emailU,gender,kilograms,santimeters,0);

                    Intent intent = new Intent();
                    intent.putExtra("username", usernameR.getText().toString());
                    intent.putExtra("password", passR.getText().toString());
                    setResult(RESULT_REG_SUCCESSFUL, intent);
                    finish();
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
