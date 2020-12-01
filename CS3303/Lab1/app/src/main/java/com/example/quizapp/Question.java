package com.example.quizapp;
/*
    Utility class to create question statements and store value either true or false

 */

public class Question {
    private String statement;
    private boolean answer;
    private boolean status = false;

    public void setStatement(String newStatement){
        statement = newStatement;
    }
    public void setAnswer(boolean ans){
        answer = ans;
    }
    public void setStatus(boolean newStatus) { status = newStatus; }

    public String getStatement(){ return statement; }
    public boolean getAnswer(){ return answer; }
    public boolean getStatus() { return status; }
}
