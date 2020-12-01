package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    TextView item_tv;
    Intent intent;

    HashMap<String, Integer> order;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        item_tv = findViewById(R.id.item);
        TableLayout table = findViewById(R.id.table);
        intent = getIntent();

        order = (HashMap<String, Integer>) intent.getSerializableExtra("ORDER");

        String item = intent.getStringExtra("TO_ADD");
        item_tv.setText(item);

        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        TextView t1, t2;
        TableRow row;

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "Item"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText(String.format(Locale.US, "%s", "Quantity"));
        t2.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30,0,0,0);
        t1.setWidth(700);

        t1.setTextColor(Color.WHITE);
        t2.setTextColor(Color.WHITE);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.setBackgroundColor(Color.rgb(128,0,0));

        table.addView(row);

        int i = 0;
        for (HashMap.Entry<String, Integer> entry : order.entrySet()) {
            if (entry.getValue() > 0) {
                t1 = new TextView(this);
                t1.setText(entry.getKey());

                t2 = new TextView(this);
                t2.setText(String.format(Locale.US, "%,d", entry.getValue()));

                t1.setPadding(30,0,0,0);
                t1.setWidth(700);

                row = new TableRow(this);
                row.setLayoutParams(lp);
                row.addView(t1);
                row.addView(t2);

                if (i % 2 == 0)
                    row.setBackgroundColor(Color.rgb(171,171,171));
                i++;

                table.addView(row);
            }
        }
    }

    @Override
    public void finish() {
        EditText amount_et = findViewById(R.id.amount);
        String s_amount = amount_et.getText().toString();
        if (s_amount.length() > 0) {
            int i_amount = Integer.parseInt(s_amount);
            String item = item_tv.getText().toString();

            if (i_amount > 0 && order.get(item) > 0)
                Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
            else if (i_amount > 0)
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            else if (order.get(item) > 0)
                Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();

            order.put(item, i_amount);
        }
        intent.putExtra("ORDER", order);
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
