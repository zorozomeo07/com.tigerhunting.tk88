package com.tigerhunting.tk88.Dinosaur;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tigerhunting.tk88.MainActivity;
import com.tigerhunting.tk88.R;

public class OVerGame extends AppCompatActivity {

    private ImageButton playAgain = null;
    private ImageButton mainMenu = null;
    private ImageButton submit = null;
    private TextView textScore;


    int userScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TK88);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        playAgain = (ImageButton) findViewById(R.id.playAgain);
        mainMenu = (ImageButton) findViewById(R.id.mainMenu);


        textScore = (TextView) findViewById(R.id.highestScoreLabel);

        Intent intent = getIntent();
        userScore = intent.getExtras().getInt("a");
        System.out.println(userScore);
        textScore.setText("Chúc mừng  bạn giành chiến thắng "+ userScore+ " điểm");

    }

    public void playAgain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void returnMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}


