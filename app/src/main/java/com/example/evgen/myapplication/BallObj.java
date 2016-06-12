package com.example.evgen.myapplication;

        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;

/**
 * Created by Evgen on 08.05.2016.
 */
public class BallObj {
    double xPos;
    double yPos;
    int angle;
    int radius;


    public  BallObj(double xPos, double yPos, int angle, int radius){
        this.angle = angle;
        this.yPos = yPos;
        this.xPos = xPos;
        this.radius = radius;

    }


    public void setAngle(int angle){
        this.angle = angle;
    }

    public int getAngle(){
        return this.angle;
    }

    public int getRadius(){
        return this.radius;
    }

    public double getyPos(){

        return this.yPos;
    }

    public double getxPos(){

        return this.xPos;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((int)xPos, (int)yPos, radius, paint);
    }



    public void step(int getWidth,int getHeight){
        xPos += (int)1* Math.cos(Math.toRadians(angle));
        yPos += (int)1* Math.sin(Math.toRadians(angle));

        if (xPos >= getWidth - radius+5) angle =180-angle;
        if (yPos >= getHeight - radius+5) angle =360-angle;
        if (xPos <=  radius+5) angle = 180-angle;
        if (yPos <=  radius+5) angle = 360-angle;
        if (angle>360) angle -=360;
    }

    public void hitBalls (BallObj b){
        int tempAngel = this.getAngle();
        this.setAngle(b.getAngle());
        b.setAngle(tempAngel);

    }

    public boolean ballConflict (BallObj b){
        double distance = Math.hypot(b.getxPos()-this.getxPos(),b.getyPos()-this.getyPos());
        return (distance < b.getRadius() + this.getRadius());
    }


}
