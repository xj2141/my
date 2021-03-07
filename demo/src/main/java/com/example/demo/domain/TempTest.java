package com.example.demo.domain;

public class TempTest {

    private int testId;
    private String testDate;
    private String testTime;

    @Override
    public String toString() {
        return "TempTest{" +
                "testId=" + testId +
                ", testDate='" + testDate + '\'' +
                ", testTime='" + testTime + '\'' +
                ", testPlace='" + testPlace + '\'' +
                ", vv='" + vv + '\'' +
                ", qmax='" + qmax + '\'' +
                ", ft='" + ft + '\'' +
                ", tqmax='" + tqmax + '\'' +
                ", flowBeginId=" + flowBeginId +
                ", flowEndId=" + flowEndId +
                ", conclusion='" + conclusion + '\'' +
                '}';
    }

    private String testPlace;
    private String vv;
    private String qmax;
    private String ft;
    private String tqmax;
    private int flowBeginId;
    private int flowEndId;
    private String conclusion;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getTestPlace() {
        return testPlace;
    }

    public void setTestPlace(String testPlace) {
        this.testPlace = testPlace;
    }

    public String getVv() {
        return vv;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    public String getQmax() {
        return qmax;
    }

    public void setQmax(String qmax) {
        this.qmax = qmax;
    }

    public String getFt() {
        return ft;
    }

    public void setFt(String ft) {
        this.ft = ft;
    }

    public String getTqmax() {
        return tqmax;
    }

    public void setTqmax(String tqmax) {
        this.tqmax = tqmax;
    }

    public int getFlowBeginId() {
        return flowBeginId;
    }

    public void setFlowBeginId(int flowBeginId) {
        this.flowBeginId = flowBeginId;
    }

    public int getFlowEndId() {
        return flowEndId;
    }

    public void setFlowEndId(int flowEndId) {
        this.flowEndId = flowEndId;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
