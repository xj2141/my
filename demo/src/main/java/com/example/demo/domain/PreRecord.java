package com.example.demo.domain;

public class PreRecord {

    private String recordDate;
    private String wakeTime;
    private String sleepTime;
    private int beginId;
    private int endId;

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(String wakeTime) {
        this.wakeTime = wakeTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getBeginId() {
        return beginId;
    }

    public void setBeginId(int beginId) {
        this.beginId = beginId;
    }

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }
}
