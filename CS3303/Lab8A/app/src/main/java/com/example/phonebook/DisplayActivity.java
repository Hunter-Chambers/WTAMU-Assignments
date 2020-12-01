package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    @TargetApi(26)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        String [] projection = {EntryProvider.COLUMN_FIRST_NAME,
                EntryProvider.COLUMN_LAST_NAME,
                EntryProvider.COLUMN_PHONE_NUMBER};
        Cursor cursor = getContentResolver().query(EntryProvider.CONTENT_URI, projection,
                null, null);
        int count = cursor.getCount();
        cursor.moveToFirst();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow tableRow = new TableRow(this);
        TextView t0,t1,t2;
        t0 = new TextView(this);
        t1 = new TextView(this);
        t2 = new TextView(this);
        t0.setPadding(20, 20, 20, 20);
        t1.setPadding(20, 20, 20, 20);
        t2.setPadding(20, 20, 20, 20);
        t0.setTextColor(Color.BLUE);
        t1.setTextColor(Color.BLUE);
        t2.setTextColor(Color.RED);
        t0.setTypeface(null, Typeface.BOLD);
        t1.setTypeface(null, Typeface.BOLD);
        t2.setTypeface(null, Typeface.BOLD);
        t0.setText("First name");
        t1.setText("Last Name");
        t2.setText("Phone Number");
        tableRow.addView(t0);
        tableRow.addView(t1);
        tableRow.addView(t2);
        tableLayout.addView(tableRow);
        for(int i = 0; i < count; i++){
            tableRow = new TableRow(this);
            t0 = new TextView(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t0.setTypeface(null, Typeface.BOLD);
            t1.setTypeface(null, Typeface.BOLD);
            t2.setTypeface(null, Typeface.BOLD);
            t0.setText(cursor.getString(cursor.getColumnIndex("firstName")));
            t1.setText(cursor.getString(cursor.getColumnIndex("lastName")));
            t2.setText(cursor.getString(cursor.getColumnIndex("phoneNumber")));
            t0.setTextColor(Color.BLACK);
            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            t0.setTypeface(null, Typeface.BOLD);
            t1.setTypeface(null, Typeface.BOLD);
            t2.setTypeface(null, Typeface.BOLD);
            t0.setPadding(20, 20, 20, 20);
            t1.setPadding(20, 20, 20, 20);
            t2.setPadding(20, 20, 20, 20);
            tableRow.addView(t0);
            tableRow.addView(t1);
            tableRow.addView(t2);
            tableLayout.addView(tableRow);
            if (!cursor.isLast()) cursor.moveToNext();
        }
        cursor.close();
        setContentView(tableLayout);
    }

}