package com.example.evelina.befit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkStateChangedReceiver extends BroadcastReceiver {
    public NetworkStateChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(!(activeNetwork!=null&&activeNetwork.isConnectedOrConnecting())){
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}
