package com.tesi.pingpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrador on 08/02/2016.
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback {
    private Hilo hilo;
    private Paint paintBlack, paintTexto,paintBlanco,paintVerde;
    private int ancho, alto;
    private Barra barra1, barra2;
    private Pelota pelota;
    private int puntos1 = 0, puntos2 = 0;
    private long ultimoTiempo;

    public MainView(Context context)
    {
        super(context);
        inicia();
    }

    public MainView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        inicia();
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        inicia();
    }

    private void inicia()
    {
        paintBlack = new Paint();
        paintBlack.setColor(Color.CYAN);
        paintBlack.setStyle(Paint.Style.FILL);

        paintBlanco = new Paint();
        paintBlanco.setColor(Color.WHITE);
        paintBlanco.setStyle(Paint.Style.FILL);
        paintBlanco.setStrokeWidth(10);

        paintVerde = new Paint();
        paintVerde.setColor(Color.GREEN);
        paintVerde.setStyle(Paint.Style.FILL);
        paintVerde.setStrokeWidth(7);

        paintTexto = new Paint();
        paintTexto.setColor(Color.RED);
        paintTexto.setTextSize(46);
        paintTexto.setTypeface(Typeface.create("Arial", Typeface.NORMAL));

        barra1 = new Barra(0, 0, 0, 0,getContext(),false);
        barra2 = new Barra(0, 0, 0, 0,getContext(),true);
        pelota = new Pelota(0, 0, 0, 0, 0,getContext());

        ultimoTiempo = System.currentTimeMillis();

        getHolder().addCallback(this);
        hilo = new Hilo(getHolder());
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        hilo.setRunning(true);
        hilo.start();
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        barra1.y = event.getY();
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        ancho = width;
        alto = height;

        barra1.x = ancho / 20;
        barra1.y = alto / 2;
        barra1.alto = alto / 5;
        barra1.ancho = ancho / 40+20;

        barra2.x = 19 * ancho / 20-80;
        barra2.y = alto / 2;
        barra2.alto = alto / 5;
        barra2.ancho = ancho / 40+20;
        barra2.dy = 1;

        pelota.x = ancho / 2;
        pelota.y = alto / 2;
        pelota.dx = ancho / 200;
        pelota.dy = ancho / 200;
        pelota.radio = ancho / 50;

        Log.d("tag", "xyz barra1: " + barra1);
        Log.d("tag", "xyz barra2: " + barra2);
    }

    public void actualiza()
    {
        long t = System.currentTimeMillis();
        if ((t - ultimoTiempo) >= 5000) {
            ultimoTiempo = t;
            barra2.dy = 5;

        }

        pelota.mueve();
        if (pelota.x < 0) {
            puntos2++;
            pelota.x = ancho / 2;
            pelota.y = alto / 2;
        }
        else if (pelota.x > ancho) {
            puntos1++;
            if(puntos1 == 10){
                pelota.dx *= 1.2;
                pelota.dy *= 1.2;
            }
            pelota.x = ancho / 2;
            pelota.y = alto / 2;
        }

        pelota.rebota(alto);
        pelota.rebota(barra1);
        pelota.rebota(barra2);

        if (pelota.dy < 0) {
            barra2.mueveArriba();
        }
        else {
            barra2.mueveAbajo(alto);
        }
    }

    public void pinta(Canvas canvas)
    {
        if (canvas == null) return;

        canvas.drawRect(0, 0, ancho, alto, paintBlack);
        canvas.drawLine(0, alto / 2, ancho, alto/2,paintBlanco);
        canvas.drawLine(ancho/2,0,ancho/2,alto,paintVerde);

        barra1.pinta(canvas);
        barra2.pinta(canvas);
        pelota.pinta(canvas);
        canvas.drawText(puntos1 + "", ancho / 5, alto / 20, paintTexto);
        canvas.drawText(puntos2 + "", 4 * ancho / 5, alto / 15, paintTexto);
        //Log.d("tag", "xyz pelota: " + pelota);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    private class Hilo extends Thread {
        private SurfaceHolder holder;
        private boolean isRunning = false;

        public Hilo(SurfaceHolder h)
        {
            holder = h;
        }

        public void setRunning(boolean run)
        {
            isRunning = run;
        }

        @Override
        public void run()
        {
            while (isRunning) {
                actualiza();

                Canvas c = null;
                try {
                    c = this.holder.lockCanvas(null);
                    synchronized (this.holder) {
                        MainView.this.pinta(c);
                    }
                }
                finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}
