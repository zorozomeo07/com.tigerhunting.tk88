package com.tigerhunting.tk88.San_Cop;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tigerhunting.tk88.R;

public class Tho_San {
    Bitmap Thosan;
    int x, y;
    public Tho_San(Context context){
        Thosan = BitmapFactory.decodeResource(context.getResources(), R.drawable.thosan);
       x=50;
       y= Cop_View.DY-getY1();
    }

    public Bitmap getThosan() {
        return Thosan;
    }

    public int getX1() {
        return Thosan.getWidth();
    }

    public int getY1() {
        return Thosan.getHeight();
    }

}
