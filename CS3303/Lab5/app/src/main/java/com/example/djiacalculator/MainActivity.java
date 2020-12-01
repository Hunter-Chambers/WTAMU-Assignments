package com.example.djiacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

/**
 * Name:        Hunter Chambers, hc998658
 *
 * Date:        3/5/2020
 *
 * Description: This app gathers the previous day close from each of the symbols defined in
 *              'String[] symbols', adds them up, and then divides that total by the Dow Constant
 *              that is defined by 'final double DOW_CONSTANT' in the 'calculateAverage' class.
 *              The app gathers the stock information by connecting to a website by using
 *              URL, URLConnection, InputStreamReader, and BufferedReader objects.
 *              NOTE:
 *                  Since this app requires internet access, the AndroidManifest.xml needs
 *                  to include that fact.
 *                  i.e.:
 *                  <uses-permission android:name="android.permission.INTERNET"/>
 */

public class MainActivity extends AppCompatActivity {

    ProgressBar progress_bar;
    TextView average_display, time_display;
    Button compute;
    String[] symbols = {"AAPL", "AXP", "BA", "CAT", "CSCO", "CVX", "DIS", "DOW", "GS",
                        "HD", "IBM", "INTC", "JNJ", "JPM", "KO", "MCD", "MMM", "MRK",
                        "MSFT", "NKE", "PFE", "PG", "TRV", "UNH", "UTX", "V", "VZ",
                        "WBA", "WMT", "XOM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        average_display = findViewById(R.id.row_3);
        time_display = findViewById(R.id.row_2);
        progress_bar = findViewById(R.id.progress_bar);
        compute = findViewById(R.id.button);

        progress_bar.setVisibility(View.INVISIBLE);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new calculateAverage().execute(symbols);
            }
        });
    }

    private class calculateAverage extends AsyncTask<String, Integer, String> {

        long begin, end;
        double time_taken;
        String average;
        int i;
        final double DOW_CONSTANT = 0.14748071991788;

        @Override
        protected String doInBackground(String[] symbols) {
            try {
                i = 1;
                double sum = 0;
                int length = symbols.length;
                int tick = (length / 10);
                while (i <= length) {
                    String symbol = symbols[i - 1];
                    URL url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/" +
                            symbol + "?interval=2m");
                    URLConnection urlConnection = url.openConnection();
                    InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = bufferedReader.readLine();

                    String[] arr = line.split("\"symbol\":");
                    String[] s1 = arr[1].split(",");

                    if (!(s1[0].equals('"' + symbol + '"')))
                        throw new IOException();

                    arr = line.split("\"previousClose\":");
                    s1 = arr[1].split(",");
                    sum += Double.parseDouble(s1[0]);

                    inputStreamReader.close();
                    bufferedReader.close();

                    if (i % tick == 0)
                        publishProgress(i / tick);

                    i++;
                }
                average = String.format(Locale.US,
                        "DJIA for the previous day close is:\n\n$%,.2f", (sum / DOW_CONSTANT));
            } catch (MalformedURLException mle) {
                mle.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                average = String.format(Locale.US, "Could not retrieve symbol %s", symbols[i - 1]);
            }
            return average;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            publishProgress(0);
            progress_bar.setVisibility(View.VISIBLE);
            average_display.setText(String.format(Locale.US, "DJIA computation in progress..."));
            time_display.setText("");
            begin = System.nanoTime();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            end = System.nanoTime();
            time_taken = ((double)(end - begin) / 1000000000.00);
            progress_bar.setVisibility(View.INVISIBLE);
            SpannableString str = new SpannableString(String.format(Locale.US, "Time taken for the computation: %,.2f sec", time_taken));
            str.setSpan(new StyleSpan(Typeface.BOLD), 32, 40, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            time_display.setText(str);
            str = new SpannableString(String.format(Locale.US, "%s", s));
            str.setSpan(new RelativeSizeSpan(2f), 37, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(new ForegroundColorSpan(Color.rgb(40, 56, 115)), 37, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //average_display.setText(String.format(Locale.US, "%s", s));
            average_display.setText(str);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress_bar.setProgress(values[0]);
        }

    }

}
