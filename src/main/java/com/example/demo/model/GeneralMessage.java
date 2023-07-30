package com.example.demo.model;

public class GeneralMessage {
    private String introductionMessage;
    private int trackingCode;

    public GeneralMessage() {
    }

    public GeneralMessage(String introductionMessage, int trackingCode) {
        this.introductionMessage = introductionMessage;
        this.trackingCode = trackingCode;
    }

    public String getIntroductionMessage() {
        return introductionMessage;
    }

    public void setIntroductionMessage(String introductionMessage) {
        this.introductionMessage = introductionMessage;
    }

    public int getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(int trackingCode) {
        this.trackingCode = trackingCode;
    }
}
