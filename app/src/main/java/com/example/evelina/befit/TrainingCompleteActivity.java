package com.example.evelina.befit;

import android.content.Intent;
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

import java.util.Date;

public class TrainingCompleteActivity extends AppCompatActivity {
    TextView challengeName;
    Button ok;
    String username;
    String challengeNameString;
    Challenge challenge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_complete);
        challengeName= (TextView)findViewById(R.id.congrats_training_name);
        challengeNameString=getIntent().getStringExtra("challengeName");
        challengeName.setText(challengeNameString);
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
        ok= (Button)findViewById(R.id.ok_complete);
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
}
