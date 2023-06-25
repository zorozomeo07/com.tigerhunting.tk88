package com.tigerhunting.tk88;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerhunting.tk88.Dinosaur.GameTrex;
import com.tigerhunting.tk88.San_Cop.Play_game;


public class MainActivity extends AppCompatActivity {

    ImageView Cop,  t_but;
    ImageView Trex;
    String link_app="https://xinhtv41.com/tk88/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Cop =findViewById(R.id.cop_game);
        Trex =findViewById(R.id.trex_game);
        t_but=findViewById(R.id.t_but);
        t_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(link_app)));
            }
        });


        //click
        Cop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Play_game.class));
            }
        });
        Trex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, GameTrex.class));
            }
        });



    }
    public void Web(String app_web){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(app_web));
        startActivity(intent);
    }


}