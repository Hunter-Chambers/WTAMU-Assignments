package com.example.loancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

import static java.lang.StrictMath.abs;

public class TableActivity extends AppCompatActivity {

    TableLayout table;
    Intent intent;
    TextView loan_display, payments_display;
    double total_interest = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        table = findViewById(R.id.table);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

        intent = getIntent();
        double loan = intent.getDoubleExtra("LOAN", 0);
        int num_of_payments = intent.getIntExtra("PAYMENTS", 0);
        double amount = intent.getDoubleExtra("AMOUNT", 0);
        double rate = intent.getDoubleExtra("RATE", 0);

        loan_display = findViewById(R.id.loan);
        payments_display = findViewById(R.id.payments);

        loan_display.append(String.format(Locale.US, " $%,.2f", loan));
        payments_display.append(String.format(Locale.US, " %d", num_of_payments));

        TextView t1, t2, t3, t4, t5;
        TableRow row;

        t1 = new TextView(this);
        t1.setText(String.format(Locale.US, "%s", "#"));
        t1.setTypeface(null, Typeface.BOLD);
        t2 = new TextView(this);
        t2.setText(String.format(Locale.US, "%s", "Monthly"));
        t2.setTypeface(null, Typeface.BOLD);
        t3 = new TextView(this);
        t3.setText(String.format(Locale.US, "%s", "Interest"));
        t3.setTypeface(null, Typeface.BOLD);
        t4 = new TextView(this);
        t4.setText(String.format(Locale.US, "%s", "Principle"));
        t4.setTypeface(null, Typeface.BOLD);
        t5 = new TextView(this);
        t5.setText(String.format(Locale.US, "%s", "Balance"));
        t5.setTypeface(null, Typeface.BOLD);

        t1.setPadding(30,0,30,0);
        t2.setPadding(0,0,40, 0);
        t3.setPadding(0,0,40, 0);
        t4.setPadding(0,0,40, 0);
        t5.setPadding(0,0,30, 0);

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        row.addView(t5);

        t1.setTextColor(Color.WHITE);
        t2.setTextColor(Color.WHITE);
        t3.setTextColor(Color.WHITE);
        t4.setTextColor(Color.WHITE);
        t5.setTextColor(Color.WHITE);
        row.setBackgroundColor(Color.rgb(128,0,0));

        table.addView(row);

        double interest, after_interest;
        for(int i = 1; i <= num_of_payments; i++) {
            t1 = new TextView(this);
            t1.setText(String.format(Locale.US, "%d.", i));
            t1.setTypeface(null, Typeface.BOLD);

            t2 = new TextView(this);
            t2.setText(String.format(Locale.US, "$%,.2f", amount));

            interest = (loan * rate);
            total_interest += interest;
            t3 = new TextView(this);
            t3.setText(String.format(Locale.US, "$%,.2f", interest));

            after_interest = (amount - interest);
            t4 = new TextView(this);
            t4.setText(String.format(Locale.US, "$%,.2f", after_interest));

            loan = abs(loan - after_interest);
            t5 = new TextView(this);
            t5.setText(String.format(Locale.US, "$%,.2f", loan));

            t1.setPadding(30,0,30,0);
            t2.setPadding(0,0,40, 0);
            t3.setPadding(0,0,40, 0);
            t4.setPadding(0,0,40, 0);
            t5.setPadding(0,0,30, 0);

            row = new TableRow(this);
            row.setLayoutParams(lp);
            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);
            row.addView(t5);

            if (i % 2 == 0 && i != num_of_payments)
                row.setBackgroundColor(Color.rgb(171,171,171));
            else if (i == num_of_payments) {
                t1.setTextColor(Color.WHITE);
                t2.setTextColor(Color.WHITE);
                t3.setTextColor(Color.WHITE);
                t4.setTextColor(Color.WHITE);
                t5.setTextColor(Color.WHITE);
                row.setBackgroundColor(Color.BLACK);
            }

            table.addView(row);
        }
    }

    @Override
    public void finish() {
        intent.putExtra("TOT_INTEREST", total_interest);
        setResult(RESULT_OK, intent);
        super.finish();
    }

}
