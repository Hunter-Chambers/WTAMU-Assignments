package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TableLayout table = findViewById(R.id.order_table);
        Intent intent = getIntent();

        HashMap<String, Integer> order = (HashMap<String, Integer>) intent.getSerializableExtra("ORDER");
        HashMap<String, Double> prices = (HashMap<String, Double>) intent.getSerializableExtra("PRICES");

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

        TextView t1, t2, t3;
        TableRow row;

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "Item"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText(String.format(Locale.US, "%s", "Quantity"));
        t2.setTypeface(null, Typeface.BOLD);
        t3 = new TextView(this);
        t3.setText(String.format(Locale.US, "%s", "Price"));
        t3.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30, 0, 0, 0);
        t1.setWidth(600);
        t2.setWidth(300);

        t1.setTextColor(Color.WHITE);
        t2.setTextColor(Color.WHITE);
        t3.setTextColor(Color.WHITE);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.setBackgroundColor(Color.rgb(128, 0, 0));

        table.addView(row);

        double sub_total = 0.00;
        int i = 0;
        for (HashMap.Entry<String, Integer> entry : order.entrySet()) {
            if (entry.getValue() > 0) {
                int quantity = entry.getValue();
                double price = prices.get(entry.getKey());

                sub_total += ((double) quantity * price);

                t1 = new TextView(this);
                t1.setText(entry.getKey());
                t2 = new TextView(this);
                t2.setText(String.format(Locale.US, "%,d", quantity));
                t3 = new TextView(this);
                t3.setText(String.format(Locale.US, "$%,.2f", price));

                t1.setPadding(30,0,0,0);
                t1.setWidth(600);
                t2.setWidth(300);

                row = new TableRow(this);
                row.setLayoutParams(lp);
                row.addView(t1);
                row.addView(t2);
                row.addView(t3);

                if (i % 2 == 0)
                    row.setBackgroundColor(Color.rgb(171,171,171));
                i++;

                table.addView(row);
            }
        }

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "Sub Total"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText("");
        t3 = new TextView(this);
        t3.setText(String.format(Locale.US, "$%,.2f", sub_total));
        t3.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30, 0, 0, 0);
        t1.setWidth(600);
        t2.setWidth(300);

        t1.setTextColor(Color.WHITE);
        t2.setTextColor(Color.WHITE);
        t3.setTextColor(Color.WHITE);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.setBackgroundColor(Color.rgb(0, 0, 0));

        table.addView(row);

        double tax = (0.0825 * sub_total);

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "Tax (8.25%)"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText("");
        t3 = new TextView(this);
        t3.setText(String.format(Locale.US, "$%,.2f", tax));
        t3.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30, 0, 0, 0);
        t1.setWidth(600);
        t2.setWidth(300);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);

        table.addView(row);

        double total = (tax + sub_total);

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "Total"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText("");
        t3 = new TextView(this);
        t3.setText(String.format(Locale.US, "$%,.2f", total));
        t3.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30, 0, 0, 0);
        t1.setWidth(600);
        t2.setWidth(300);

        t1.setTextColor(Color.WHITE);
        t3.setTextColor(Color.WHITE);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.setBackgroundColor(Color.rgb(0, 0, 0));

        table.addView(row);
    }
}
