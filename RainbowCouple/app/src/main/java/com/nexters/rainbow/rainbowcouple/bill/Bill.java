package com.nexters.rainbow.rainbowcouple.bill;

import java.util.Date;

public class Bill {

    private int year;
    private int month;
    private int day;
    private String userSN;
    private String userName;
    private String category;
    private int amount;
    private String comment;

    public Bill(int year, int month, int day, String userSN, String userName, String category, int amount, String comment) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.userSN = userSN;
        this.userName = userName;
        this.category = category;
        this.amount = amount;
        this.comment = comment;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getUserSN() {
        return userSN;
    }

    public void setUserSN(String userSN) {
        this.userSN = userSN;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
