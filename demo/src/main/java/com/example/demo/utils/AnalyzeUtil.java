package com.example.demo.utils;

import com.example.demo.domain.Test;

//尿流率分析
public class AnalyzeUtil {

    public static Test analyze(){
        Test testResult=new Test();
        testResult.setVv("1");
        testResult.setFt("2");
        testResult.setConclusion("3");
        testResult.setQmax("4");
        testResult.setTqmax("5");
        return testResult;
    }
}
