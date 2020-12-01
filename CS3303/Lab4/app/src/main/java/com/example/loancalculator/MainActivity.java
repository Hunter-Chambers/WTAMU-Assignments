package com.example.loancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static java.lang.StrictMath.pow;

public class MainActivity extends AppCompatActivity {

    EditText loan_input, apr_input, yrs_input;
    TextView payment, interest;
    Button calculate, reset, amr_table;
    double loan, apr, rate, amount;
    int yrs;
    final int MY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loan_input = findViewById(R.id.loan_amount);
        apr_input = findViewById(R.id.apr_amount);
        yrs_input = findViewById(R.id.years_amount);

        payment = findViewById(R.id.loan_payment);
        interest = findViewById(R.id.interest);

        calculate = findViewById(R.id.calc_button);
        reset = findViewById(R.id.reset_button);
        amr_table = findViewById(R.id.table_button);

        reset.setEnabled(false);
        amr_table.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        if (request_code == MY_RESULT && result_code == RESULT_OK)
            interest.setText(String.format(Locale.US,
                    "Over the period of the loan, interest paid: $%,.2f",
                    data.getDoubleExtra("TOT_INTEREST", 0)));
    }

    public void tableActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), TableActivity.class);
        intent.putExtra("LOAN", loan);
        intent.putExtra("PAYMENTS", (yrs * 12));
        intent.putExtra("AMOUNT", amount);
        intent.putExtra("RATE", rate);
        startActivityForResult(intent, MY_RESULT);
    }

    public void compute(View view) {
        try {
            loan = Double.parseDouble(loan_input.getText().toString().replaceAll("[$,]", ""));
            apr = Double.parseDouble(apr_input.getText().toString().replace("%", ""));
            yrs = Integer.parseInt(yrs_input.getText().toString());

            loan_input.setText(String.format(Locale.US, "$%,.2f", loan));
            apr_input.setText(String.format(Locale.US, "%,.2f%%", apr));
            yrs_input.setText(String.format(Locale.US, "%d", yrs));

            rate = (apr / 100 / 12);
            double denom = (pow((1 + rate), (yrs * 12)) - 1);

            amount = (loan * (rate + (rate / denom)));

            payment.setText(String.format(Locale.US, "$%,.2f", amount));

            reset.setEnabled(true);
            amr_table.setEnabled(true);
            interest.setText("");
        } catch (Exception e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(View view) {
        loan_input.setText("");
        apr_input.setText("");
        yrs_input.setText("");
        payment.setText("");
        interest.setText("");

        reset.setEnabled(false);
        amr_table.setEnabled(false);

        loan_input.requestFocus();
    }

}
