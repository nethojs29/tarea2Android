package com.tesi.pingpong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrador on 08/02/2016.
 */
public class Pelota {
    public float x, y, dx, dy, radio;
    private Paint paint;
    private RectF rect;
    Bitmap b;

    public Pelota(float x, float y, float dx, float dy, float radio,Context context) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.radio = radio;

        rect = new RectF(x, y, x + radio, y + radio);
        b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        paint = new Paint();
        //paint.setStyle(Paint.Style.FILL);
        //paint.setColor(0xFF00FF00);

    }

    public void mueve()
    {
        x += dx;
        y += dy;
        rect.set(x, y, x + radio, y + radio);
    }

    public void rebota(int alto)
    {
        if (y < 0 || y > alto) {
            rebotaY();
        }
    }

    public void rebota(Barra b)
    {
        if (rect.intersect(b.x, b.y, b.x + b.ancho, b.y + b.alto)) {
            rebotaX();
        }
    }

    private void rebotaX()
    {
        dx = -dx;
    }

    private void rebotaY()
    {
        dy = -dy;
    }

    public void pinta(Canvas canvas) {
        //canvas.drawCircle(x, y, radio, paint);
        canvas.drawBitmap(b,x,y,paint);
    }

    public String toString()
    {
        return "x: " + x + " y: " + y + " dx: " + dx + " dy: " + dy;
    }
}
