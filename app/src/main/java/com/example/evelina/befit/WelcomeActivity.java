package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.User;

public class WelcomeActivity extends AppCompatActivity {
    Button start;
    User user;
    Button shortcut;
    private NetworkStateChangedReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        start = (Button) findViewById(R.id.button_start);
        shortcut= (Button) findViewById(R.id.shortcut);
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,TabbedActivity.class);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbManager.getInstance(WelcomeActivity.this).loadUsers();
                StringBuilder allUsersS = new StringBuilder();
                for (User u : DbManager.getInstance(WelcomeActivity.this).allUsers.values()) {
                    allUsersS.append(u.getUsername()).append(" ").append(u.getPassword()).append("\n");
                }
                Log.e("maikaTi", allUsersS.toString());
                if (WelcomeActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", null) != null) {
                    Log.e("USER", WelcomeActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", null));
                    user = getCurrentUser(WelcomeActivity.this);
                }
                maintainLogin(WelcomeActivity.this);

            }
        });
    }

    public void maintainLogin(Context activity){
        boolean logged_in=activity.getSharedPreferences("Login", Context.MODE_PRIVATE).getBoolean("logged_in", false);

        if (logged_in) {
            Toast.makeText(WelcomeActivity.this,"Going to Home",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(WelcomeActivity.this, TabbedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("loggedUser",user) ;
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(WelcomeActivity.this,"Going to Login",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(WelcomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    public  User getCurrentUser(Context activity){
        String username=activity.getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", null);
        User user=DbManager.getInstance(WelcomeActivity.this).getUser(username);
        return user;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
