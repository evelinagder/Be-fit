package com.example.evelina.befit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class ExerciseInventoryActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_inventory);
        toolbar= (Toolbar) findViewById(R.id.toolbar_exercise_inventory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Exercise inventory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
