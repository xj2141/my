package com.example.demo.domain;

public class TempSufRecord {
    private int recordId;
    private String username;
    private String flowTime;
    private String capacity;
    private String flowFastYN;
    private String flowLeakYN;
    private String remark;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(String flowTime) {
        this.flowTime = flowTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getFlowFastYN() {
        return flowFastYN;
    }

    public void setFlowFastYN(String flowFastYN) {
        this.flowFastYN = flowFastYN;
    }

    public String getFlowLeakYN() {
        return flowLeakYN;
    }

    public void setFlowLeakYN(String flowLeakYN) {
        this.flowLeakYN = flowLeakYN;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
