package com.example.demo.utils;

import com.example.demo.domain.Flow;
import com.example.demo.domain.Test;

import java.util.List;

//尿流率分析
public class AnalyzeUtil {

    public static Test analyze(List<Flow> flows,int vv){
        Test testResult=new Test();
        double qmax=0;
        int tqmax;
        int start=(int)Double.parseDouble(flows.get(0).getTime());;
        int middle=0;
        int end=(int)Double.parseDouble(flows.get(flows.size()-1).getTime());
        int ft=end-start;
        for(int i=0;i<flows.size();i++){
            Flow flow=flows.get(i);
            if(Double.parseDouble(flow.getRate())>qmax){
                qmax=Double.parseDouble(flow.getRate());
                middle=(int)Double.parseDouble(flow.getTime());
            }
        }
        tqmax=middle-start;
        testResult.setQmax(String.valueOf(qmax));
        testResult.setFt(String.valueOf(ft));
        testResult.setTqmax(String.valueOf(tqmax));

        String conclusion="";
        if(vv<150){
            conclusion="排尿量过低，无法测定结果";
        }else if(vv<400){
            conclusion="健康";
        }else if(vv<500){
            conclusion="逼尿肌过度牵拉，收缩力下降";
        }else{
            conclusion="排尿量过高，无法测定结果";
        }
        testResult.setConclusion(conclusion);
        return testResult;
    }
}
