package com.example.quizapp;
/*
     This app has question statements and the user of the app can test their knowledge
     on the validity of it by clicking the true or false button. Then the app will display
     a toast that says the answer is true or false. The app has a next button to
     navigate to the next question and so on.
     author: rajan alex
     version: 01/28/2020

 */
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button yes, no, prev, next;
    TextView questionLabel;
    TextView scoreLabel;
    Question [] questionBank;
    int i = 0;
    int myColors[] = {Color.BLUE, Color.RED, Color.GREEN, Color.DKGRAY, Color.MAGENTA};
    int score = 0;
    String scoreStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        no = findViewById(R.id.false_button);
        yes = findViewById(R.id.true_button);
        prev = findViewById(R.id.prev_button);
        next = findViewById(R.id.next_button);
        questionLabel = findViewById(R.id.q_label);
        scoreLabel = findViewById(R.id.score_label);
        questionBank = new Question[5];
        questionBank[0] = new Question();
        questionBank[0].setStatement("Mexico City is the capital of Mexico");
        questionBank[0].setAnswer(true);
        questionBank[1] = new Question();
        questionBank[1].setStatement("Toronto is the capital of Canada");
        questionBank[1].setAnswer(false);
        questionBank[2] = new Question();
        questionBank[2].setStatement("Milan is the capital of Italy");
        questionBank[2].setAnswer(false);
        questionBank[3] = new Question();
        questionBank[3].setStatement("London is the capital of United Kingdom");
        questionBank[3].setAnswer(true);
        questionBank[4] = new Question();
        questionBank[4].setStatement("Jerusalem is the capital of Israel");
        questionBank[4].setAnswer(true);
        questionLabel.setText(questionBank[i].getStatement());
        questionLabel.setTextColor(myColors[i]);
        scoreStr = ("Your score is: " + score + "/5");
        scoreLabel.setText(scoreStr);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify(false);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify(true);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i <= 0) i = questionBank.length - 1;
                else i--;
                questionLabel.setText(questionBank[i].getStatement());
                questionLabel.setTextColor(myColors[i]);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i >= questionBank.length - 1) i = 0;
                else i++;
                questionLabel.setText(questionBank[i].getStatement());
                questionLabel.setTextColor(myColors[i]);
            }
        });
    }

    private void verify(boolean b){
        if (!questionBank[i].getStatus()) {
            questionBank[i].setStatus(true);
            if (questionBank[i].getAnswer() == b) {
                score++;
                Toast.makeText(MainActivity.this, "Correct answer",
                                Toast.LENGTH_SHORT).show();
                scoreStr = ("Your score is: " + score + "/5");
                scoreLabel.setText(scoreStr);
            }
            else
                Toast.makeText(MainActivity.this, "Sorry, your answer is incorrect",
                                Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "You have already attempted this answer",
                            Toast.LENGTH_SHORT).show();
        }
    }
}
