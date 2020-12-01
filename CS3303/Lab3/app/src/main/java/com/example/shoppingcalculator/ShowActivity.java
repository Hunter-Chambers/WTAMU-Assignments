package com.example.shoppingcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    TextView showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        showData = findViewById(R.id.showData);
        display();
    }

    @Override
    public void finish(){
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        super.finish();
    }

    public void display() {
        Bundle extras = getIntent().getExtras();
        showData.setText("");
        for (String key : extras.keySet()) {
            if (key.charAt(0) == '@') {
                String[] value = extras.getString(key).split("-", 2);
                String name = key.replaceAll("@", "").toUpperCase();
                showData.append(String.format("%-9s%-9s%s\n", name, value[0], value[1]));
            }
        }
    }
}
