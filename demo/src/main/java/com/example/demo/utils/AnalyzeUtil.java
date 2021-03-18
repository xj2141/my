package com.example.demo.utils;

import com.example.demo.domain.Flow;
import com.example.demo.domain.Test;

import java.util.ArrayList;
import java.util.List;

//尿流率分析
public class AnalyzeUtil {

    //曲线平滑算法
    public static List<Flow> smooth(List<Flow> flows){
        List<Flow>list=new ArrayList<>();
        double a=Double.parseDouble(flows.get(0).getTime());
        double b=Double.parseDouble(flows.get(0).getRate());
        Flow flow1=new Flow();
        flow1.setTime(String.format("%.1f",a));
        flow1.setRate(String.format("%.1f",b));
        list.add(flow1);
        int length=flows.size();
        for(int i=0;i<length;i++){
            if(i<=length-4){
                for(double t=0.0;t<=1.0;t+=0.1){
                    double a1 =  Math.pow((1 - t), 3) / 6;
                    double a2 =  (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 4) / 6;
                    double a3 = (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2) + 3 * t + 1) / 6;
                    double a4 =  Math.pow(t, 3) / 6;

                    double x=a1*Double.parseDouble(flows.get(i).getTime())+a2*Double.parseDouble(flows.get(i+1).getTime())+a3*Double.parseDouble(flows.get(i+2).getTime())+a4*Double.parseDouble(flows.get(i+3).getTime());
                    double y=a1*Double.parseDouble(flows.get(i).getRate())+a2*Double.parseDouble(flows.get(i+1).getRate())+a3*Double.parseDouble(flows.get(i+2).getRate())+a4*Double.parseDouble(flows.get(i+3).getRate());
                    Flow flow=new Flow();
                    flow.setTime(String.format("%.1f",x));
                    flow.setRate(String.format("%.1f",y));
                    list.add(flow);
                }
            }
        }
        a=Double.parseDouble(flows.get(length-1).getTime());
        b=Double.parseDouble(flows.get(length-1).getRate());
        Flow flow2=new Flow();
        flow2.setTime(String.format("%.1f",a));
        flow2.setRate(String.format("%.1f",b));
        list.add(flow2);
        return list;
    }

    //尿流曲线的特征提取和分析
    public static Test analyze(List<Flow> flows,int vv,String sex){
        if(sex.equals("")){
            sex="男";
        }
        Test testResult=new Test();
        int length=flows.size();
        //获取尿流开始时间
        double start=0;
        double temp=Double.parseDouble(flows.get(0).getRate());
        if(temp>=(-1e-6)&&temp<=(1e-6)){
            for(int i=1;i<length;i++){
                temp=Double.parseDouble(flows.get(i).getRate());
                if(!(temp>=(-1e-6)&&temp<=(1e-6))){
                    start=Double.parseDouble(flows.get(i-1).getTime());
                    break;
                }
            }
        }else{
            start=Double.parseDouble(flows.get(0).getTime());
        }
        //获取尿流结束时间
        double end=0;
        double tem=Double.parseDouble(flows.get(length-1).getRate());
        if(tem>=(-1e-6)&&tem<=(1e-6)){
            for(int i=length-2;i>=0;i--){
                tem=Double.parseDouble(flows.get(i).getRate());
                if(!(tem>=(-1e-6)&&tem<=(1e-6))){
                    end=Double.parseDouble(flows.get(i+1).getTime());
                    break;
                }
            }
        }else{
            end=Double.parseDouble(flows.get(length-1).getTime());
        }
        //计算尿流时间
        double ft=end-start;
        testResult.setFt(String.valueOf(ft));
        //计算最大尿流率、达峰时间
        double qmax=0;
        double middle=0;
        for(int i=0;i<length;i++){
            Flow flow=flows.get(i);
            if(Double.parseDouble(flow.getRate())>qmax){
                qmax=Double.parseDouble(flow.getRate());
                middle=Double.parseDouble(flow.getTime());
            }
        }
        double tqmax=middle-start;
        testResult.setQmax(String.valueOf(qmax));
        testResult.setTqmax(String.valueOf(tqmax));
        //计算平均尿量率
        double qave=(double)vv/ft;
        testResult.setQave(String.format("%.1f",qave));
        //分析得出结论
        String conclusion="";
        if(vv<150){
            conclusion="排尿量过低，无法测定结果";
        }else if(vv<400){
            if(sex.equals("女")){
                if(qmax<10){
                    conclusion="梗阻存在";
                }else if(qmax<25){
                    conclusion="可疑梗阻";
                }else{
                    conclusion="排除梗阻";
                }
            }else{
                if(qmax<10){
                    conclusion="梗阻存在";
                }else if(qmax<20){
                    conclusion="可疑梗阻";
                }else{
                    conclusion="排除梗阻";
                }
            }
        }else if(vv<500){
            conclusion="逼尿肌过度牵拉，收缩力下降";
        }else{
            conclusion="排尿量过高，无法测定结果";
        }
        testResult.setConclusion(conclusion);
        return testResult;
    }
}
