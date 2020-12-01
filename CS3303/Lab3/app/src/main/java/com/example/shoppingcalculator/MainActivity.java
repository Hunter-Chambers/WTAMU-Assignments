package com.example.shoppingcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Group Members: Hunter Chambers, hc998658
 *                Layton Easterly, le1010274
 *
 * Version:       2/18/2020
 *
 * Description:   This is a simple shopping helper app
 *
 *                Takes item name, price, quantity and sales tax input from the user
 *                to calculate total cost of the item. User is also able to input
 *                more items, which will update the total cost. User can also display
 *                a list of all items at any time. This app utilizes three activities
 *                to initially compute, add a new item, and show the item list.
 */

public class MainActivity extends AppCompatActivity {

    final int MY_REQUEST_CODE = 1;

    Button addItem, showList, compute;
    TextView totalOutput;
    EditText itemName, itemPrice, itemQuantity, tax;
    Intent mainIntent;
    int totItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // buttons
        addItem = findViewById(R.id.addItem);
        addItem.setEnabled(false);
        showList = findViewById(R.id.showList);
        showList.setEnabled(false);
        compute = findViewById(R.id.compute);

        // edit text fields
        itemName = findViewById(R.id.itemInput);
        itemPrice = findViewById(R.id.priceInput);
        itemQuantity = findViewById(R.id.quantityInput);
        tax = findViewById(R.id.taxInput);

        // text view fields
        totalOutput = findViewById(R.id.totalOutput);

        // intent object
        mainIntent = new Intent(getApplicationContext(), MainActivity.class);
    }

    public void calculate(View view) {
        try {
            double price = Double.parseDouble(itemPrice.getText().toString().replaceAll("[$]", ""));
            itemPrice.setText(String.format("$%,.2f", price));

            double quantity = Double.parseDouble(itemQuantity.getText().toString().replaceAll("[$]", ""));
            itemQuantity.setText(String.format("%,.2f", quantity));

            double taxInput = Double.parseDouble(tax.getText().toString().replaceAll("[%]", ""));
            tax.setText(String.format("%,.2f%%", taxInput));

            double subtotal = (price * quantity);
            totalOutput.setText(String.format("$%,.2f", (subtotal + (subtotal * taxInput / 100))));

            itemName.setEnabled(false);
            itemPrice.setEnabled(false);
            itemQuantity.setEnabled(false);
            tax.setEnabled(false);

            compute.setEnabled(false);
            addItem.setEnabled(true);
            showList.setEnabled(true);

            totItems++;

            mainIntent.putExtra("ITEMS", String.valueOf(totItems));
            mainIntent.putExtra("TOTAL", totalOutput.getText().toString());
            mainIntent.putExtra("TAX", String.valueOf(taxInput));
            mainIntent.putExtra(("@" + itemName.getText().toString().toLowerCase()),
                                (itemPrice.getText().toString() + "-" +
                                 itemQuantity.getText().toString()));
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Invalid Field Input",
                            Toast.LENGTH_SHORT).show();
        }
    }

    public void itemActivity(View view) {
        Intent itemIntent = new Intent(getApplicationContext(), ItemActivity.class);
        Bundle extras = mainIntent.getExtras();
        for (String key : extras.keySet())
            itemIntent.putExtra(key, extras.getString(key));
        startActivityForResult(itemIntent, MY_REQUEST_CODE);
    }

    public void showActivity(View view) {
        Intent showIntent = new Intent(getApplicationContext(), ShowActivity.class);
        Bundle extras = mainIntent.getExtras();
        for (String key : extras.keySet())
            showIntent.putExtra(key, extras.getString(key));
        startActivityForResult(showIntent, MY_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemName.setText("");
        itemPrice.setText("");
        itemQuantity.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {
            for (String key : data.getExtras().keySet())
                mainIntent.putExtra(key, data.getExtras().getString(key));
            totalOutput.setText(mainIntent.getExtras().getString("TOTAL"));
            totItems = Integer.parseInt(mainIntent.getExtras().getString("ITEMS"));
        }
    }

}
