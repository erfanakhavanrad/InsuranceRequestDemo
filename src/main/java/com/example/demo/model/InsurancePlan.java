package com.example.demo.model;

public class InsurancePlan {
    private String title;
    private double fee;
    private int month;

    public InsurancePlan() {
    }

    public InsurancePlan(String title, double fee, int month) {
        this.title = title;
        this.fee = fee;
        this.month = month;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
