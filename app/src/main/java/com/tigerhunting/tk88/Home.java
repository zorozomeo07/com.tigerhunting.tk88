package com.tigerhunting.tk88;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    String AccessTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Home.this,MainActivity.class));
            }
        },1200);


    }
}