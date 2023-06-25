package com.tigerhunting.tk88.Dinosaur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerhunting.tk88.R;


public class GameTrex extends AppCompatActivity {
    private ImageButton start = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TK88);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_khong_long);
        start = (ImageButton) findViewById(R.id.start);
    }

    public void newGame(View view) {
        startActivity(new Intent(getApplicationContext(), GameInDo.class));
    }


}
