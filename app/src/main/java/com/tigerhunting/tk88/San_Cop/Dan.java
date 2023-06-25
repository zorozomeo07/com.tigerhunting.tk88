package com.tigerhunting.tk88.San_Cop;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tigerhunting.tk88.R;


public class Dan {
    int dan_x, dan_y;
    Bitmap dan;
    Dan(Context context, int x, int y){
        dan = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        this.dan_x =x;
        this.dan_y =y;
    }

    public Bitmap getDan() {
        return dan;
    }

    public int getDan_x() {
        return dan.getWidth();
    }
}
