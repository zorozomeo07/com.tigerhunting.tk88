package com.tigerhunting.tk88.San_Cop;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerhunting.tk88.R;


public class Play_game extends AppCompatActivity {
    ImageView play_game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TK88);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_game);
        play_game =findViewById(R.id.start_game);
        play_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cop_View copView_ =new Cop_View(Play_game.this);
                setContentView(copView_);
            }
        });
    }
}