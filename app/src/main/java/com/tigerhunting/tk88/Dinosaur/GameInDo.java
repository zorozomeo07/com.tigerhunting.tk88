package com.tigerhunting.tk88.Dinosaur;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.tigerhunting.tk88.R;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameInDo extends AppCompatActivity {

    private final static String TAG = "PlayGameActivity";


    private ImageButton unduck = null;
    Rect r1 = new Rect();
    Rect r2 = new Rect();
    Rect r3 = new Rect();
    Rect r4 = new Rect();

    private TextView scoreLabel;
    private int scoreTime = 0;
    private Timer timer = null;

    boolean isGameOver = false;
    private ValueAnimator i_vamation;
    private AnimatorSet set;
    AnimationDrawable a_dino, ani_2,a_duck;

    Animation ani3;
    ImageView imh1, img2, img3, img4, img5;
    boolean isBaroReady = false;
    boolean isAccReady = false;

    boolean isJumping = false;
    boolean isPrepareJump = false;
    boolean isDucking = false;

    private double absolutePressure;
    private boolean rungame = true;
    private float[] agame = null;
    private float[] bgame = null;
    Matric_Game filter;

    double abc1 =0;
    double alt=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TK88);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_game);

        buttons();
        filter = Matric_Game.kalmanInitial();
        anima_game();
        gai();
        shaowgame();
        one_game();

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
    }


    private void one_game() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor mag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        SensorEventListener sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    float pressure = event.values[0];
                    if (rungame) {
                        absolutePressure = pressure;
                        rungame = false;
                    }
                    alt = 44300 * (1 - Math.pow(pressure / absolutePressure, 0.19));
                    isBaroReady = true;
                }
                if ((agame != null) && (bgame != null)
                        && (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)) {

                    float[] deviceRelativeAcceleration = new float[4];
                    deviceRelativeAcceleration[0] = event.values[0];
                    deviceRelativeAcceleration[1] = event.values[1];
                    deviceRelativeAcceleration[2] = event.values[2];
                    deviceRelativeAcceleration[3] = 0;

                    float[] R = new float[16], I = new float[16], earthAcc = new float[16];

                    SensorManager.getRotationMatrix(R, I, agame, bgame);

                    float[] inv = new float[16];

                    android.opengl.Matrix.invertM(inv, 0, R, 0);
                    android.opengl.Matrix.multiplyMV(earthAcc, 0, inv, 0, deviceRelativeAcceleration, 0);
                    if (earthAcc[2] < -1 && !isJumping && !isDucking ) isPrepareJump = true;
                    if (earthAcc[2] > 3.9 && isPrepareJump && !isJumping && !isDucking){
                        dino3();
                        isPrepareJump = false;
                        isDucking = false;
                    }

                    abc1 = earthAcc[2];
                    isAccReady = true;

                } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                    agame = event.values;
                } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    bgame = event.values;
                }

                if (isAccReady && isBaroReady) {
                    kendino((float) abc1, (float) alt);

                    if (filter.getRvone()[1] < -0.8 && !isDucking && !isJumping) {
                        dino1();
                    }else if (filter.getRvone()[1] > 0.5 && isDucking){
                        dino2();
                    }

                    isBaroReady = false;
                    isAccReady = false;
                }
            }

            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL );
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL );
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        scoreTime = 0;
        isGameOver = false;
        timer = new Timer();
        setTimerTask();
    }

    private void setTimerTask() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGameOver) {
                    scoreTime++;
                }
                scoreLabel.setText("SCORE : " + scoreTime);
            }
        }, 0, 1000);
    }



    private void anima_game() {
        //Animate Dino
        img2 = (ImageView) findViewById(R.id.dino_animation);
        img2.setBackgroundResource(R.drawable.ani1);
        a_dino = (AnimationDrawable) img2.getBackground();
        //Animate Dino Ducking
        img3 = (ImageView) findViewById(R.id.dino_ducking_animation);
        img3.setBackgroundResource(R.drawable.ani2);
        a_duck = (AnimationDrawable) img3.getBackground();
        img3.setVisibility(View.GONE);
        // Animate Ground
        img5 = (ImageView) findViewById(R.id.ground_animation);
        img5.setBackgroundResource(R.drawable.ani4);
        ani_2 = (AnimationDrawable) img5.getBackground();
        ani3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.dat);
        //Animate Dino Jumping
        img4 = (ImageView) findViewById(R.id.dino_jump_animation);
        img4.setBackgroundResource(R.drawable.dino);
        img4.setVisibility(View.GONE);
    }

    public void shaowgame() {
        a_dino.start();

        img5.startAnimation(ani3);
        i_vamation = ValueAnimator.ofFloat(1200, -300);
        i_vamation.setDuration(3000);
        i_vamation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imh1.setX((Float) animation.getAnimatedValue());
                sprite2rect(r1, imh1);
                if(isDucking){
                    sprite2rect(r3, img3);
                } else{
                    sprite2rect(r4, img2);
                }
                if (!isGameOver && isCollisionDetected()){
                    gameOver();
                }

            }
        });
        i_vamation.start();
        i_vamation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gai();
                animation.start();
            }
        });
    }



    private void anistop() {

        a_dino.stop();
        img5.clearAnimation();
        i_vamation.pause();
        if (isJumping) set.pause();
        //dinoSprite = (ImageView) findViewById(R.id.dino_dead);
        img2.setBackgroundResource(R.drawable.dead);
        img3.setBackgroundResource(R.drawable.dead);
        img4.setBackgroundResource(R.drawable.dead);
    }
    private void gameOver() {

        isGameOver = true;
        anistop();


        timer.cancel();
        timer = null;

        Intent intent = new Intent(GameInDo.this, OVerGame.class);
        intent.putExtra("a",scoreTime);
        startActivity(intent);
    }

    private boolean isCollisionDetected() {
        if (!isJumping && !isDucking){
            if (Rect.intersects(r1, r4)){
                return true;
            }
        } else if (isJumping) {
            if (Rect.intersects(r1, r2)) {
                return true;
            }
        }
        else if (isDucking){
            if (Rect.intersects(r1, r3)){

                return true;
            }
        }
        return false;
    }
    private void gai() {
        Random random = new Random();

        int randomNumber = random.nextInt(6);

        switch (randomNumber) {
            case 1:
                imh1 = (ImageView) findViewById(R.id.obstacle1_animation);
                imh1.setBackgroundResource(R.drawable.anigai2);
                break;
            case 2:
                imh1 = (ImageView) findViewById(R.id.obstacle2_animation);
                imh1.setBackgroundResource(R.drawable.anigai3);
                break;
            case 3:
                imh1 = (ImageView) findViewById(R.id.obstacle3_animation);
                imh1.setBackgroundResource(R.drawable.obstacle3_animation);
                break;
            case 4:
                imh1 = (ImageView) findViewById(R.id.obstacle4_animation);
                imh1.setBackgroundResource(R.drawable.obstacle4_animation);
                break;
            case 5:
                imh1 = (ImageView) findViewById(R.id.obstacle5_animation);
                imh1.setBackgroundResource(R.drawable.obstacle5_animation);
                break;
            default:
                imh1 = (ImageView) findViewById(R.id.obstacle0_animation);
                imh1.setBackgroundResource(R.drawable.anigai1);
                break;
        }
    }

    private void buttons() {


        ImageButton jump = (ImageButton) findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dino3();
            }
        });

        ImageButton duck = (ImageButton) findViewById(R.id.duck);
        duck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dino1();
            }
        });

        ImageButton unduck = (ImageButton) findViewById(R.id.unduck);
        unduck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dino2();
            }
        });
    }

    private void sprite2rect(Rect rectangle, ImageView animation) {
        rectangle.left = (int) animation.getX() + 30;
        rectangle.top = (int) animation.getY() + 30;
        rectangle.bottom = (int) (animation.getY() + animation.getHeight());
        rectangle.right = (int) (animation.getX() + animation.getWidth() - 30);
    }





    private void dino3() {
        if(isDucking){
            dino2();
        }

        if(!isJumping){
            float y = img2.getY();
            ValueAnimator jumpUpAnimation = ValueAnimator.ofFloat(y, y-350);
            jumpUpAnimation.setDuration(350);
            jumpUpAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    img4.setY((Float) animation.getAnimatedValue());
                    sprite2rect(r2, img4);
                }
            });
            ValueAnimator jumpDownAnimation = ValueAnimator.ofFloat(y-350, y);
            jumpDownAnimation.setDuration(350);
            jumpDownAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    img4.setY((Float) animation.getAnimatedValue());
                    sprite2rect(r2, img4);
                }
            });
            jumpUpAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isJumping = true;
                    isDucking = false;
                    a_dino.stop();
                    img2.setVisibility(View.GONE);
                    img3.setVisibility(View.GONE);
                    img4.setVisibility(View.VISIBLE);
                }
            });
            jumpDownAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isJumping = false;
                    a_dino.start();
                    img2.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.GONE);
                }
            });
            set = new AnimatorSet();
            set.playSequentially(jumpUpAnimation, jumpDownAnimation);
            set.start();
        }
    }
    private void dino1(){
        isDucking = true;
        isJumping = false;
        a_duck.start();
        img3.setVisibility(View.VISIBLE);
        a_dino.stop();
        img2.setVisibility(View.GONE);
    }

    private void dino2(){
        isDucking = false;
        a_duck.stop();
        img3.setVisibility(View.GONE);
        a_dino.start();
        img2.setVisibility(View.VISIBLE);
    }

    public void kendino(float accelerometer, float barometer) {
        RealVector u = new ArrayRealVector(new double[] { accelerometer });
        RealVector z = new ArrayRealVector(new double[] { barometer});

        filter.predict(u);
        filter.correct(z);
    }

}








