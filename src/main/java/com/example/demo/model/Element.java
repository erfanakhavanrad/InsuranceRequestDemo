package com.example.demo.model;

import org.springframework.boot.SpringApplication;

public class Element {

    private int bodyInsuranceAmount;
    private int thirdPartyInsuranceAmount;
    private int trackingCode;
    private String name;
    private String lastName;
    private String nationalCode;
    private boolean verified;


    public int getBodyInsuranceAmount() {
        return bodyInsuranceAmount;
    }

    public void setBodyInsuranceAmount(int bodyInsuranceAmount) {
        this.bodyInsuranceAmount = bodyInsuranceAmount;
    }

    public int getThirdPartyInsuranceAmount() {
        return thirdPartyInsuranceAmount;
    }

    public void setThirdPartyInsuranceAmount(int thirdPartyInsuranceAmount) {
        this.thirdPartyInsuranceAmount = thirdPartyInsuranceAmount;
    }

    public int getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(int trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
