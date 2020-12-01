package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
    Name:           Hunter Chambers, hc998658

    Version:        04/06/2020

    Description:    This app utilizes SQLite to maintain a simple database of names and
                    phone numbers. The user must fill out all fields before an entry can be
                    added to the database. Only first and last name must be specified in order
                    for a record to be deleted from the database. All entries in the database
                    are displayed in a separate activity.
 */

public class MainActivity extends AppCompatActivity {
    EditText fname, lname, area_code, prefix_code, four_digs;
    String first_name, last_name, area, prefix, last_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = findViewById(R.id.fname_et);
        lname = findViewById(R.id.lname_et);
        area_code = findViewById(R.id.area_code);
        prefix_code = findViewById(R.id.num_prefix);
        four_digs = findViewById(R.id.four_digs);
    }

    public void add(View view){
        first_name = fname.getText().toString();
        last_name = lname.getText().toString();
        area = area_code.getText().toString();
        prefix = prefix_code.getText().toString();
        last_four = four_digs.getText().toString();

        if (first_name.length() <= 0 || last_name.length() <= 0 || area.length() <= 0 ||
            prefix.length() <= 0 || last_four.length() <= 0)
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        else if (area.length() < 3 || prefix.length() < 3 || last_four.length() < 4)
            Toast.makeText(this, "Phone number must be 10 digits long", Toast.LENGTH_SHORT).show();
        else {
            String number = (area_code.getText().toString() + prefix_code.getText().toString() +
                    four_digs.getText().toString());
            Entry entry = new Entry(fname.getText().toString(), lname.getText().toString(),
                    number);
            ContentValues values = new ContentValues();
            values.put(EntryProvider.COLUMN_FIRST_NAME, entry.getFname());
            values.put(EntryProvider.COLUMN_LAST_NAME, entry.getLname());
            values.put(EntryProvider.COLUMN_PHONE_NUMBER, entry.getNumber());
            Uri uri = getContentResolver().insert(EntryProvider.CONTENT_URI, values);
            fname.setText("");
            lname.setText("");
            area_code.setText("");
            prefix_code.setText("");
            four_digs.setText("");
            Toast.makeText(getApplicationContext(), "Entry successfully added", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view){
        first_name = fname.getText().toString();
        last_name = lname.getText().toString();

        if (first_name.length() <= 0 || last_name.length() <= 0)
            Toast.makeText(this, "Please enter a first and last name", Toast.LENGTH_SHORT).show();
        else {
            String selection = ("firstName = \"" + fname.getText().toString() + "\" and lastName = \"" +
                    lname.getText().toString() + "\"");
            int result = getContentResolver().delete(EntryProvider.CONTENT_URI, selection, null);
            if (result > 0) {
                fname.setText("");
                lname.setText("");
                area_code.setText("");
                prefix_code.setText("");
                four_digs.setText("");
                if (result == 1)
                    Toast.makeText(getApplicationContext(), result + " entry successfully deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), result + " entries successfully deleted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "No entry found", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view) {
        fname.setText("");
        lname.setText("");
        area_code.setText("");
        prefix_code.setText("");
        four_digs.setText("");
        fname.requestFocus();
    }

    public void showAll(View view){
        fname.setText("");
        lname.setText("");
        area_code.setText("");
        prefix_code.setText("");
        four_digs.setText("");
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}