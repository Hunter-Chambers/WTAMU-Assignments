package com.example.pricecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button calculate, clr;
    EditText inPrice, inQuantity;
    TextView outSubtotal, outTax, outTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculate = findViewById(R.id.compute);
        clr = findViewById(R.id.clear);
        inPrice = findViewById(R.id.inputPrice);
        inQuantity = findViewById(R.id.inputQuantity);
        outSubtotal = findViewById(R.id.outputSubtotal);
        outTax = findViewById(R.id.outputTax);
        outTotal = findViewById(R.id.outputTotal);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runCalculation();
            }
        });
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScreen();
            }
        });
        outSubtotal.setVisibility(View.INVISIBLE);
        outTax.setVisibility(View.INVISIBLE);
        outTotal.setVisibility(View.INVISIBLE);
    }

    public void runCalculation() {
        try {
            double price = Double.parseDouble(inPrice.getText().toString().replaceAll("[$\\s]", ""));
            int quantity = Integer.parseInt(inQuantity.getText().toString());

            double subtotal = (quantity * price);
            double taxTotal = (subtotal * 0.075);
            double total = (subtotal + taxTotal);

            inPrice.setText(String.format(Locale.US, "$%,.2f", price));
            outSubtotal.setText(String.format(Locale.US, "$%,.2f", subtotal));
            outTax.setText(String.format(Locale.US, "$%,.2f", taxTotal));
            outTotal.setText(String.format(Locale.US, "$%,.2f", total));

            outSubtotal.setVisibility(View.VISIBLE);
            outTax.setVisibility(View.VISIBLE);
            outTotal.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Invalid field input",
                            Toast.LENGTH_SHORT).show();
        }
    }

    public void clearScreen() {
        inPrice.setText("");
        inQuantity.setText("");
        outSubtotal.setVisibility(View.INVISIBLE);
        outTax.setVisibility(View.INVISIBLE);
        outTotal.setVisibility(View.INVISIBLE);
    }
}
