package com.example.demo.controller;

import com.example.demo.domain.TempFlow;
import com.example.demo.domain.TempTest;
import com.example.demo.service.TempFlowTestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, List> get() {
        Map<String, List> map = new HashMap<String, List>();
        List<TempTest>pre=tempFlowTestService.getTest();
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

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(TempTest tempTest,List<TempFlow>tempFlows){
        tempFlowTestService.insertFlow(tempFlows);
        int count=tempFlows.size();
        int lastId=tempFlowTestService.getLastIdFlow();
        int firstId=lastId-count+1;
        tempTest.setFlowBeginId(firstId);
        tempTest.setFlowEndId(lastId);
        tempFlowTestService.insertTest(tempTest);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(TempTest tempTest,List<TempFlow>tempFlows){
        tempFlowTestService.deleteFlow(tempTest.getFlowBeginId(),tempTest.getFlowEndId());
        tempFlowTestService.insertFlow(tempFlows);
        int count=tempFlows.size();
        int lastId=tempFlowTestService.getLastIdFlow();
        int firstId=lastId-count+1;
        tempTest.setFlowBeginId(firstId);
        tempTest.setFlowEndId(lastId);
        tempFlowTestService.updateTest(tempTest);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(TempTest tempTest){
        tempFlowTestService.deleteFlow(tempTest.getFlowBeginId(),tempTest.getFlowEndId());
        tempFlowTestService.deleteTest(tempTest.getTestId());
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(){
        tempFlowTestService.removeTest();
        tempFlowTestService.removeFlow();
    }
}
