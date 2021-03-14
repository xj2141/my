package com.example.demo.domain;

public class Flow {

    private int id;
    private String rate;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "id=" + id +
                ", rate='" + rate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
