package com.tesi.pingpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrador on 08/02/2016.
 */
public class Barra {
    public float x, y, dy, alto, ancho;
    private Paint paint;
    Bitmap bitmap;

    public Barra(float x, float y, float dy, int w, int h,Context context,boolean isMachine)
    {
        this.x = x;
        this.y = y;
        this.dy = dy;
        ancho = w;
        alto = h;

        bitmap = BitmapFactory.decodeResource(context.getResources(),isMachine ? R.drawable.raqizq : R.drawable.raqder);

        paint = new Paint();
        //paint.setColor(0xffd2691e);
        //paint.setStyle(Paint.Style.FILL);
    }

    public Barra(float x, float y, int w, int h,Context context,boolean isMachine)
    {
        this(x, y, -1, w, h, context, isMachine);
    }

    public void mueve()
    {
        y += dy;
    }

    public void mueveArriba()
    {
        y -= dy;
        if (y < 0) {
            y = 0;
        }
    }

    public void mueveAbajo(int h)
    {
        y += dy;
        if ((y + alto) > h) {
            y = h - alto;
        }
    }

    public void pinta(Canvas canvas)
    {
        canvas.drawBitmap(bitmap,x,y,paint);
        //canvas.drawRect(x, y, x + ancho, y + alto, paint);
    }

    public String toString()
    {
        return "x: " + x + " y: " + y + " ancho: " + ancho + " alto: " + alto;
    }
}
