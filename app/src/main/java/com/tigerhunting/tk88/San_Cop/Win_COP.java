package com.tigerhunting.tk88.San_Cop;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerhunting.tk88.MainActivity;
import com.tigerhunting.tk88.R;


public class Win_COP extends AppCompatActivity {

    TextView out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TK88);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cop_win);
        innit();

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Win_COP.this, MainActivity.class));
            }
        });
    }
    private void innit() {
        out =findViewById(R.id.tt_bo);
    }
}