package com.example.demo.domain;

public class TempPreRecord {

    private String recordDate;
    private String wakeTime;
    private String sleepTime;

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

    public TempPreRecord(){
        this.recordDate="";
        this.wakeTime="";
        this.sleepTime="";
    }
}
