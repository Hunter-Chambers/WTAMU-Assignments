package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

/*
    Name:           Hunter Chambers, hc998658

    Version:        04/10/2020

    Description:    This app utilizes ImageButtons to navigate to a different activity where
                    the user can input how many they want of the item displayed in the image.
                    The PROCESS ORDER button takes the user to a different activity where
                    their entire order is displayed and their total, including tax, is also
                    displayed.
 */

public class StoreActivity extends AppCompatActivity {
    final int MY_RESULT = 1;

    HashMap<String, Integer> order;
    HashMap<String, Double> prices;

    ImageButton dasani_btn, oatmeal_btn, hotcakes_btn,
            sbiscuit_btn, bebiscuit_btn, esbiscuit_btn, burrito_btn;
    Button process_btn;
    Intent add_intent, process_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dasani_btn = findViewById(R.id.dasani_btn);
        oatmeal_btn = findViewById(R.id.fruit_oatmeal_btn);
        hotcakes_btn = findViewById(R.id.hotcakes_btn);
        sbiscuit_btn = findViewById(R.id.sausage_biscuit_btn);
        bebiscuit_btn = findViewById(R.id.bacon_egg_biscuit_btn);
        esbiscuit_btn = findViewById(R.id.egg_sausage_biscuit_btn);
        burrito_btn = findViewById(R.id.sausage_burrito_btn);

        order = new HashMap<>();
        order.put(dasani_btn.getContentDescription().toString(), 0);
        order.put(oatmeal_btn.getContentDescription().toString(), 0);
        order.put(hotcakes_btn.getContentDescription().toString(), 0);
        order.put(sbiscuit_btn.getContentDescription().toString(), 0);
        order.put(bebiscuit_btn.getContentDescription().toString(), 0);
        order.put(esbiscuit_btn.getContentDescription().toString(), 0);
        order.put(burrito_btn.getContentDescription().toString(), 0);

        prices = new HashMap<>();
        prices.put(dasani_btn.getContentDescription().toString(), 2.00);
        prices.put(oatmeal_btn.getContentDescription().toString(), 2.00);
        prices.put(hotcakes_btn.getContentDescription().toString(), 2.00);
        prices.put(sbiscuit_btn.getContentDescription().toString(), 1.99);
        prices.put(bebiscuit_btn.getContentDescription().toString(), 2.00);
        prices.put(esbiscuit_btn.getContentDescription().toString(), 2.00);
        prices.put(burrito_btn.getContentDescription().toString(), 1.75);

        dasani_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(dasani_btn.getContentDescription().toString());
            }
        });
        oatmeal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(oatmeal_btn.getContentDescription().toString());
            }
        });
        hotcakes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(hotcakes_btn.getContentDescription().toString());
            }
        });
        sbiscuit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(sbiscuit_btn.getContentDescription().toString());
            }
        });
        bebiscuit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(bebiscuit_btn.getContentDescription().toString());
            }
        });
        esbiscuit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(esbiscuit_btn.getContentDescription().toString());
            }
        });
        burrito_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(burrito_btn.getContentDescription().toString());
            }
        });

        process_btn = findViewById(R.id.order_btn);
        process_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOrder();
            }
        });

        add_intent = new Intent(getApplicationContext(), AddActivity.class);
        process_intent = new Intent(getApplicationContext(), OrderActivity.class);
        process_intent.putExtra("PRICES", prices);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if (request_code == MY_RESULT && result_code == RESULT_OK)
            order = (HashMap<String, Integer>) data.getSerializableExtra("ORDER");
    }

    public void addItem(String content) {
        add_intent.putExtra("TO_ADD", content);
        add_intent.putExtra("ORDER", order);
        startActivityForResult(add_intent, MY_RESULT);
    }

    public void processOrder() {
        process_intent.putExtra("ORDER", order);
        startActivity(process_intent);
    }

}