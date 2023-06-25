package com.tigerhunting.tk88.San_Cop;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.tigerhunting.tk88.R;

import java.util.ArrayList;

public class Cop_View extends View {
    Context context;
    public  static int DX,DY;
    Bitmap bg;
    Rect rect;

    Cop cop1;
    int number=50;
    Paint mau=new Paint();
    Paint over=new Paint();
    Handler handler;
    Runnable runnable;
    ArrayList<Dan> dans;
    Tho_San thoSan;
    ArrayList<Cop> cops;
    public Cop_View(Context context) {
        super(context);
        this.context=context;
        Display display=((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        DY=point.y;
        DX=point.x;
        thoSan =new Tho_San(context);
         dans =new ArrayList<>();
         cops =new ArrayList<>();
        for(int i=0;i<1;i++){
            Cop cop =new Cop(context);
            cops.add(cop);
        }
        cop1 =new Cop(context);
       handler=new Handler();
       over.setColor(Color.BLACK);
       over.setTextAlign(Paint.Align.CENTER);
       over.setTextSize(120);
         runnable=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        bg= BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_2);
        rect=new Rect(0,0,DX,DY);
        mau.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bg,null,rect,null);
        //person
        canvas.drawBitmap(thoSan.getThosan(), thoSan.x, thoSan.y,null);
        //bò
        for(int i = 0; i< cops.size(); i++){
            canvas.drawBitmap(cops.get(i).getBo(cops.get(i).frame), cops.get(i).cop_x, cops.get(i).cop_y,null);
            cops.get(i).frame++;
            cops.get(i).cop_x -=5;
            if(cops.get(i).frame>3){
                cops.get(i).frame=0;
            }
            if(thoSan.x+ thoSan.getX1()-50>= cops.get(i).cop_x){
                Game_over();
            }
            if(number>35){
                mau.setColor(Color.GREEN);
            }else if(number>20){
                mau.setColor(Color.YELLOW);
            }else if(number>0){
                mau.setColor(Color.RED);
            }else {
                canvas.drawText("Game Over",DX/2-30,DY/2-20,over);

            }
            canvas.drawRect(cops.get(i).cop_x,DY- cops.get(i).getCop_y()-30, cops.get(i).cop_x +5*number,DY- cops.get(i).getCop_y()-10,mau);

        }
        //dạn
        for(int i = 0; i< dans.size(); i++){
            dans.get(i).dan_x += 150;
            canvas.drawBitmap(dans.get(i).getDan(), dans.get(i).dan_x, dans.get(i).dan_y,null);

            if(dans.get(i).dan_x >= cop1.cop_x - cop1.getCop_x()-30){
                dans.remove(i);
                number-=2;
                System.out.print("aa"+number);
            }
            if (number<0){
                number=0;
                Game_over();

            }
        }

            handler.postDelayed(runnable,20);
    }

    private void Game_over() {
        Runnable runnables=new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, Win_COP.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread=new Thread(runnables);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP){
            Dan dan =new Dan(context, thoSan.x+ thoSan.getX1()-20, thoSan.y+15);
            dans.add(dan);
        }
        return true;
    }
}
