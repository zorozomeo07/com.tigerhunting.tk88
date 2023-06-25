package com.tigerhunting.tk88.San_Cop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tigerhunting.tk88.R;

public class Cop {
    Bitmap [] cop =new Bitmap[4];
    int cop_x; int cop_y;
    int frame=0;
    public Cop(Context context){
        cop[0]=BitmapFactory.decodeResource(context.getResources(), R.drawable.cop1);
        cop[1]=BitmapFactory.decodeResource(context.getResources(), R.drawable.cop2);
        cop[2]=BitmapFactory.decodeResource(context.getResources(), R.drawable.cop3);
        cop[3]=BitmapFactory.decodeResource(context.getResources(), R.drawable.cop4);

        cop_x = Cop_View.DX- getCop_x();
        cop_y = Cop_View.DY- getCop_y();;
    }

    public Bitmap getBo(int frame) {
        return cop[frame];
    }

    public int getCop_y() {
        return cop[0].getHeight();
    }

    public int getCop_x() {
        return cop[0].getWidth();
    }
}
