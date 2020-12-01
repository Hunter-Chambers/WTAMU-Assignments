package com.example.mycomet;

/*
 * Name:        Hunter Chambers, hc998658
 *
 * Version:     03/28/2020
 *
 * Description: This app draws a solid circle using the Canvas instance. It also utilizes
 *              the onDraw() function of the View class to draw the circle at a certain position,
 *              and then it utilizes the invalidate() function in order to call the
 *              onDraw() function again and draw the circle at a new position.
 */

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyCometActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comet);

        // to find the width and length of the UI screen use DisplayMetrics
        // and then communicate with the window manager to get the values
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            float width = displayMetrics.widthPixels;
            float height = (displayMetrics.heightPixels - 200); // -200 to avoid the bar at the bottom of the screen
            setContentView(new CometAnimation(this, width, height));
        } else
            Toast.makeText(this, "Fatal error during sleep interval", Toast.LENGTH_SHORT).show();
    }

}