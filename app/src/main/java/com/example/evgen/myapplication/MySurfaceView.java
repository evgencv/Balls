package com.example.evgen.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class MySurfaceView extends SurfaceView {
    int xPos=100;
    int yPos=100;
    int radius=2;

    final int COUNTBALLS = 20;
    private BallObj b[];
    private BallThread myThread;
    private SurfaceHolder surfaceHolder;
    private Bitmap bmpIcon;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context,
                         AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        Random r = new Random();
        b = new BallObj[COUNTBALLS];
        for (int i=1;i<COUNTBALLS;i++) {
            int radius = 5;

            b[i] = new BallObj(
                    (r.nextInt(300)+radius*2),
                    (r.nextInt(300)+radius*2),
                    r.nextInt(360),
                    radius);

        }



        myThread = new BallThread(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                myThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
}

    protected void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        for (int i=1;i<COUNTBALLS;i++){
            b[i].draw(canvas);
            b[i].step(getWidth(),getHeight());
            for (int j=1;j<COUNTBALLS;j++){
                if (j == i)continue;


                if (b[i].ballConflict(b[j])) {
                    b[i].hitBalls(b[j]);

                }
            }
        }

    }
    }

