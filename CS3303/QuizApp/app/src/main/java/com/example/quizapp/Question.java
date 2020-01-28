package com.example.quizapp;
/*
    Utility class to create question statements and store value either true or false

 */

public class Question {
    private String statement;
    private boolean answer;
    public void setStatement(String statement){
        this.statement = statement;
    }
    public void setAnswer(boolean ans){
        answer = ans;
    }
    public String getStatement(){
        return statement;

    }
    public boolean getAnswer(){
        return answer;
    }
}
