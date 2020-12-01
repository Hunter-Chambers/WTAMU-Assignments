package com.example.phonebook;

public class Entry {

    private String fname;
    private String lname;
    private String number;

    public Entry(String fn, String ln, String phone) {
        fname = fn;
        lname = ln;

        char[] temp = phone.toCharArray();
        number = "(" + temp[0] + temp[1] + temp[2] + ") " +
                 temp[3] + temp[4] + temp[5] + "-" +
                 temp[6] + temp[7] + temp[8] + temp[9];
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getNumber() {
        return number;
    }

}
