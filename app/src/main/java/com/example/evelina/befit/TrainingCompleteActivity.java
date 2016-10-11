package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.Date;

public class TrainingCompleteActivity extends AppCompatActivity {
    TextView challengeName;
    Button ok;
    String username;
    String challengeNameString;
    Challenge challenge;
    TextView heading;
    ShareDialog dialog;
    CallbackManager callbackManager;
    NetworkStateChangedReceiver receiver;
    Button myShare;
    ShareButton share ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_training_complete);
        Typeface typeface = Typeface.createFromAsset(getAssets(),  "GreatVibes.ttf");
        challengeName= (TextView)findViewById(R.id.congrats_training_name);
        ok= (Button)findViewById(R.id.ok_complete);
        myShare = (Button) findViewById(R.id.my_share);
        share = (ShareButton) findViewById(R.id.share_fb);
        dialog =new ShareDialog(this);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareOpenGraphObject object =  new ShareOpenGraphObject.Builder()
                        .putString("og:type", "challenge.challenge")
                        .putString("og:title", challengeNameString)
                        .putString("og:updated_time", DbManager.getInstance(TrainingCompleteActivity.this).getUser(username).getCustomChallenges(challengeNameString).getDateOfCompletion())
                        .build();

                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("progress.progress")
                        .putObject("challenge", object)
                        .build();
                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("challenge")
                        .setAction(action)
                        .build();
                ShareDialog.show(TrainingCompleteActivity.this, content);
            }
        });


        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        dialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        String sp = getSharedPreferences("Login", Context.MODE_PRIVATE).getString("loggedWith","none");
        if(sp.equals("facebook")){
            myShare.setVisibility(View.VISIBLE);
        }

        myShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Be Fit")
                            .setContentDescription("Managed " + challengeNameString+"!" + "here should be the level of the user")
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();

                    dialog.show(linkContent);
            }

            }
        });
        challengeNameString=getIntent().getStringExtra("challengeName");
        challengeName.setText(challengeNameString);
        heading= (TextView)findViewById(R.id.headingC) ;
        heading.setTypeface(typeface);
        username=getIntent().getStringExtra("username");
        boolean isBasic=getIntent().getExtras().getBoolean("isBasic");
        User user=DbManager.getInstance(this).getUser(username);
        if(isBasic){
            challenge= TrainingManager.getInstance().getBasicChallenges(challengeNameString);
        }
        else{
            challenge=user.getCustomChallenges(challengeNameString);
        }
        Date today= new Date();
        String mid= today+"";
        String date= mid.substring(0,10);
        DbManager.getInstance(TrainingCompleteActivity.this).updateUserCompletedChallenges(user,challenge,date);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingCompleteActivity.this, TabbedActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
