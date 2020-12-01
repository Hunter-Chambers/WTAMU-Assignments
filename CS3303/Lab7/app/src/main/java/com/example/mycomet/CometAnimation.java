package com.example.mycomet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class CometAnimation extends View {
    // position and size variables for comet
    private float cx, cy;
    private float arcX, arcY;
    private float radius = 50;

    // position and size variables for moon
    private float mx, my;

    // position variables for center of ellipse
    private float originX, originY;

    // utility variables
    private float degree;
    private float mdegree;
    private float[] starX = new float[100];
    private float[] starY = new float[100];
    private float[] starRadius = new float[100];
    private Paint starPaint = new Paint();
    private Paint sunPaint = new Paint();
    private Paint moonPaint = new Paint();
    private Paint earthPaint = new Paint();
    private Context c;

    public CometAnimation(Context context, float w, float h){
        super(context);
        c = context;

        starPaint.setStyle(Paint.Style.FILL);
        starPaint.setColor(Color.rgb(255, 255, 255));
        starPaint.setShadowLayer(25, 0, 0, Color.rgb(255, 255, 255));
        sunPaint.setStyle(Paint.Style.FILL);
        sunPaint.setColor(Color.rgb(252, 169, 3));
        sunPaint.setShadowLayer(250, 0, 0, Color.rgb(252, 169, 3));
        moonPaint.setStyle(Paint.Style.FILL);
        moonPaint.setColor(Color.rgb(200, 200, 200));
        earthPaint.setStyle(Paint.Style.FILL);
        earthPaint.setColor(Color.rgb(0, 128, 128));

        // at least radius*2 from the left, right, top, bottom edges
        float topLimit = (radius * 2);
        float leftLimit = (radius * 2);
        float rightLimit = (w - leftLimit);
        float bottomLimit = (h - topLimit);

        // find the center of the ellipse
        originX = ((leftLimit + rightLimit) / 2);
        originY = ((bottomLimit + topLimit) / 2);

        // these values used for calculating cx and cy in updatePosition()
        arcX = (rightLimit-leftLimit)/2;
        arcY = (bottomLimit-topLimit)/2;

        // have the comet start at the top of the screen
        degree = 270;
        cx = originX;
        cy = topLimit;

        // have the moon start to the right of the comet
        mdegree = 0;
        mx = (cx + 100);
        my = cy;

        // fill starX, starY, and starRadius with random values
        // so that when the stars are displayed, they will be a
        // random size and in a random position every time the app is launched
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            starX[i] = (rand.nextFloat() * w);
            starY[i] = (rand.nextFloat() * h);
            starRadius[i] = (rand.nextFloat() * 10);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set the background to black
        canvas.drawRGB(0, 0, 0);

        // draw all of the stars
        for (int i = 0; i < 100; i++)
            canvas.drawCircle(starX[i], starY[i], starRadius[i], starPaint);

        // draw the sun
        canvas.drawCircle(originX, originY, 150, sunPaint);

        // draw the moon
        canvas.drawCircle(mx, my, 25, moonPaint);

        // draw the comet (earth)
        canvas.drawCircle(cx, cy, radius, earthPaint);

        updateAll();

        try {
            // how long to display each frame, a lower number means smoother transition
            Thread.sleep(1);
        } catch (InterruptedException ie) {
            Toast.makeText(c, "Fatal error during sleep interval", Toast.LENGTH_SHORT).show();
        }

        invalidate();
    }

    private void updateAll(){
        // update to next degree position
        degree += 0.5;
        mdegree += 1.33;

        // if comet has travelled 360 degrees, then it travelled a full
        // revolution and the degree position can be reset to 0
        if (degree >= 360.0) degree -= 360.0;
        if (mdegree >= 360.0) mdegree -= 360.0;

        // calculate coordinates of comet and moon based off of degree position
        cx = originX + arcX * (float) Math.cos(Math.toRadians(degree));
        cy = originY + arcY * (float) Math.sin(Math.toRadians(degree));
        mx = cx + 100 * (float) Math.cos(Math.toRadians(mdegree));
        my = cy + 100 * (float) Math.sin(Math.toRadians(mdegree));
    }

}