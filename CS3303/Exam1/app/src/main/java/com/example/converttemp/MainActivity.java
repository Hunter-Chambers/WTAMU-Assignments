package com.example.converttemp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Button enter;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.temp_input);
        enter = findViewById(R.id.compute_button);
        output = findViewById(R.id.temp_output);
    }

    public void compute(View view) {
        try {
            double f_temp = Double.parseDouble(input.getText().toString().replaceAll(" F", ""));
            double c_temp = ((f_temp - 32.0) * 5.0 / 9.0);

            input.setText(String.format(Locale.US, "%,.2f F", f_temp));
            output.setText(String.format(Locale.US, "%,.2f C", c_temp));
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }
}
