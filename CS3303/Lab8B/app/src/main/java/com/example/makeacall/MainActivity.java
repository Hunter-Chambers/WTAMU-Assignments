package com.example.makeacall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

/*
    Name:           Hunter Chambers, hc998658

    Version:        04/06/2020

    Description:    This app utilizes the database that is built-up in the Phone Book app.
                    The user must fill out the first and last name fields, then the app searches
                    the database to see if the entry exists. It will display the entry's phone
                    number if the entry exists.
 */

public class MainActivity extends AppCompatActivity {
    private static final String AUTHORITY = "com.example.phonebook.EntryProvider";
    private static final String TABLE_ENTRY = "entry";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";

    public EditText fname, lname;
    public TextView result;
    public Button call_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname = findViewById(R.id.fname_et);
        lname = findViewById(R.id.lname_et);
        result = findViewById(R.id.result);
        call_btn = findViewById(R.id.call);
        call_btn.setEnabled(false);
    }

    public void find(View view) {
        String first_name = fname.getText().toString();
        String last_name = lname.getText().toString();

        if (first_name.length() <= 0 || last_name.length() <= 0) {
            call_btn.setEnabled(false);
            result.setText("Number:");
            Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
        } else {
            Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ENTRY);
            String[] projection = {COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE_NUMBER};
            String selection = ("firstName = \"" + fname.getText().toString() + "\" and lastName = \"" +
                    lname.getText().toString() + "\"");
            ContentResolver crInstance = getContentResolver();
            Cursor cursor = crInstance.query(CONTENT_URI, projection, selection,
                    null, null);

            String s = "Number: ";
            if (cursor.moveToFirst()) {
                s += cursor.getString(2);
                call_btn.setEnabled(true);
            } else {
                s += "Nothing was found";
                Toast.makeText(this, "No entry was found", Toast.LENGTH_SHORT).show();
                call_btn.setEnabled(false);
            }

            result.setText(s);
            cursor.close();
        }
    }

    public void MakeCall(View view) {
        String phone = result.getText().toString();
        phone = phone.substring(8);

        try {
            Uri uri = Uri.parse("tel:" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Application failed", Toast.LENGTH_LONG).show();
        }
    }

}