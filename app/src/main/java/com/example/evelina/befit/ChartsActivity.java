package com.example.evelina.befit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {
    String username;
    User user;
    PieChart chart;
    TextView yourBmi, bmiNum;
    double userBMI;

    private float [] dataY = {12, 18 ,24 ,30, 39, 42};
    private String[] dataX = {"Underweight", "Healthy", "Overweight", "Obese", "Extremely Obese"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        yourBmi= (TextView)findViewById(R.id.yourBmi);
        bmiNum= (TextView)findViewById(R.id.bmiNUm);
        Typeface typeface =  Typeface.createFromAsset(ChartsActivity.this.getAssets(),  "RockoUltraFLF.ttf");
        yourBmi.setTypeface(typeface);
        bmiNum.setTypeface(typeface);
        username = getIntent().getStringExtra("username");
        user = DbManager.getInstance(ChartsActivity.this).getUser(username);
         userBMI = calculateBMI(user.getWeight(), user.getHeight());
        bmiNum.setText(userBMI+"");

        chart = (PieChart) findViewById(R.id.chart_BMI);
        chart.setUsePercentValues(false);
        chart.setDrawHoleEnabled(true);



        // chart.setHoleColor();
        //chart.setHoleRadius(7);
        Legend legend = chart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);
        legend.setTextColor(Color.BLACK);
        addData();

    }

    private void addData() {
        ArrayList<PieEntry> yVal = new ArrayList<PieEntry>();
        for (int i = 0; i < dataY.length; i++) {
            yVal.add(new PieEntry(dataY[i], i));
        }
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < dataX.length; i++) {
            xVals.add(dataX[i]);
        } ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(12f,"Underweight",0));
        entries.add(new PieEntry(18f,"Healthy", 1));
        entries.add(new PieEntry(24f,"Overweight", 2));
        entries.add(new PieEntry(30f,"Overweight", 3));
        entries.add(new PieEntry(39f,"Obese", 4));
        entries.add(new PieEntry(42f, "Extremely Obese",5));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[] {R.color.com_facebook_blue,R.color.green,R.color.yellow,R.color.orange,R.color.red_orange,R.color.red},ChartsActivity.this);
        PieData data = new PieData(dataSet);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setData(data);



    }



    public double calculateBMI(double weight, double height) {
        Log.e("KG",weight+""+height+"");
        double bmi = (weight /((height/100)*(height/100)));
        Log.e("BMI",bmi+"");
        return bmi;

    }




}
