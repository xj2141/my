package com.example.demo.domain;

public class TempSufRecord {
    private int recordId;
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

    @Override
    public String toString() {
        return "TempSufRecord{" +
                "recordId=" + recordId +
                ", flowTime='" + flowTime + '\'' +
                ", capacity='" + capacity + '\'' +
                ", flowFastYN='" + flowFastYN + '\'' +
                ", flowLeakYN='" + flowLeakYN + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public TempSufRecord(){
        this.flowTime="";
        this.capacity="";
        this.flowFastYN="";
        this.flowLeakYN="";
        this.remark="";
    }
}
