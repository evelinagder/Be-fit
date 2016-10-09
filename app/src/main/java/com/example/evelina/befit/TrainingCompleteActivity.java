package com.example.evelina.befit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TrainingCompleteActivity extends AppCompatActivity {
    TextView challengeName;
    Button ok;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_complete);
        challengeName= (TextView)findViewById(R.id.congrats_training_name);
        challengeName.setText(getIntent().getStringExtra("challengeName"));
        username=getIntent().getStringExtra("username");
        ok= (Button)findViewById(R.id.ok_complete);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingCompleteActivity.this, TabbedActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
