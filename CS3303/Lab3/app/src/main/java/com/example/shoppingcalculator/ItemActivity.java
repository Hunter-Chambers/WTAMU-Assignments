package com.example.shoppingcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ItemActivity extends AppCompatActivity {

    TextView numOfItems;
    EditText addItemName, addItemPrice, addItemQuantity;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        addItemName = findViewById(R.id.addItemInput);
        addItemPrice = findViewById(R.id.addPriceInput);
        addItemQuantity = findViewById(R.id.addQuantityInput);

        i = getIntent();

        numOfItems = findViewById(R.id.numOfItems);
        numOfItems.append(i.getExtras().getString("ITEMS"));
    }

    @Override
    public void finish(){
        try {
            double price = Double.parseDouble(addItemPrice.getText().toString());
            addItemPrice.setText(String.format("$%,.2f", price));

            double quantity = Double.parseDouble(addItemQuantity.getText().toString());
            addItemQuantity.setText(String.format("%,.2f", quantity));

            double subtotal = (price * quantity);
            double taxInput = Double.parseDouble(i.getExtras().getString("TAX"));

            double thisTotal = (subtotal + (subtotal * taxInput / 100));
            double currentTotal = Double.parseDouble(i.getExtras().getString("TOTAL").replaceAll("[$]", ""));

            int amount = Integer.parseInt(i.getExtras().getString("ITEMS"));

            String itemName = ("@" + addItemName.getText().toString().toLowerCase());
            String notification;

            if (i.getExtras().containsKey(itemName)) {
                String[] value = i.getExtras().getString(itemName).split("-", 2);
                double currentItemPrice = Double.parseDouble(value[0].replaceAll("[$]", ""));
                double currentItemQuantity = Double.parseDouble(value[1]);
                double currentSubtotal = (currentItemPrice * currentItemQuantity);
                currentTotal -= (currentSubtotal + (currentSubtotal * taxInput / 100));
                notification = "Item successfully updated.";
            } else {
                amount++;
                notification = "Item successfully added.";
            }

            String total = String.format("$%,.2f", (thisTotal + currentTotal));

            i.putExtra("ITEMS", String.valueOf(amount));
            i.putExtra("TOTAL", total);
            i.putExtra(itemName, (addItemPrice.getText().toString() + "-" + addItemQuantity.getText().toString()));

            Toast.makeText(ItemActivity.this, notification, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ItemActivity.this, "Invalid Field Input. Item was not added.", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK, i);
        super.finish();
    }

}
