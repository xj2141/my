package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.TempFlow;
import com.example.demo.domain.TempTest;
import com.example.demo.service.TempFlowTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tempFlowTest")
public class TempFlowTestController {

    @Autowired
    private TempFlowTestService tempFlowTestService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Map<String, List> get(String username) {
        Map<String, List> map = new HashMap<String, List>();
        List<TempTest>pre=tempFlowTestService.getTest(username);
        map.put("pre",pre);
        List<List<TempFlow>>suf=new ArrayList<>();
        List<TempFlow>tempFlow=new ArrayList<>();
        int beginId=0;
        int endId=0;
        for(int i=0;i<pre.size();i++){
            beginId=pre.get(i).getFlowBeginId();
            endId=pre.get(i).getFlowEndId();
            tempFlow=tempFlowTestService.getFlow(beginId,endId);
            suf.add(tempFlow);
        }
        map.put("suf",suf);
        return map;
    }

    @RequestMapping(value = "/insertTest", method = RequestMethod.POST)
    public void insertTest(@RequestBody Map<String,String> datas){
        int count=Integer.parseInt(datas.get("count"));
        TempTest tempTest=JSONObject.parseObject(datas.get("tempTest"),TempTest.class);
        int lastId=tempFlowTestService.getLastIdFlow();
        int firstId=lastId-count+1;
        tempTest.setFlowBeginId(firstId);
        tempTest.setFlowEndId(lastId);
        tempFlowTestService.insertTest(tempTest);
    }

    @RequestMapping(value = "/insertFlow", method = RequestMethod.POST)
    public void insertFlow(@RequestBody String params){
        JSONObject jb = JSONObject.parseObject(params);
        List<TempFlow> tempFlows = JSONObject.parseArray(jb.getJSONArray("params").toJSONString(), TempFlow.class);
        tempFlowTestService.insertFlow(tempFlows);
    }

    @RequestMapping(value = "/updateDate", method = RequestMethod.POST)
    public void updateDate(String username,String testDate){
        tempFlowTestService.updateDate(username,testDate);
    }

    @RequestMapping(value = "/updateTest", method = RequestMethod.POST)
    public void updateTest(@RequestBody Map<String,String> datas){
        int count=Integer.parseInt(datas.get("count"));
        TempTest tempTest=JSON.parseObject(datas.get("tempTest"),TempTest.class);
        int lastId=tempFlowTestService.getLastIdFlow();
        int firstId=lastId-count+1;
        tempTest.setFlowBeginId(firstId);
        tempTest.setFlowEndId(lastId);
        tempFlowTestService.updateTest(tempTest);
    }

    @RequestMapping(value = "/deleteFlow", method = RequestMethod.POST)
    public void deleteFlow(TempTest tempTest){
        tempFlowTestService.deleteFlow(tempTest.getFlowBeginId(),tempTest.getFlowEndId());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(TempTest tempTest){
        tempFlowTestService.deleteFlow(tempTest.getFlowBeginId(),tempTest.getFlowEndId());
        tempFlowTestService.deleteTest(tempTest.getTestId());
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(String username){
        List<TempTest>pre=tempFlowTestService.getTest(username);
        TempTest tempTest=new TempTest();
        for(int i=0;i<pre.size();i++){
            tempTest=pre.get(i);
            this.deleteFlow(tempTest);
        }
        tempFlowTestService.removeTest(username);
    }
}
