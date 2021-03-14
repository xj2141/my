package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.FlowTestService;
import com.example.demo.service.TempFlowTestService;
import com.example.demo.service.PatientService;
import com.example.demo.utils.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flowTest")
public class FlowTestController {

    @Autowired
    private FlowTestService flowTestService;

    @Autowired
    private TempFlowTestService tempFlowTestService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Map<String, List> get(String firstDate, String lastDate, String username) {
        Map<String, List> map = new HashMap<String, List>();
        List<TestPre>pre=flowTestService.getTestPre(firstDate, lastDate,username);
        map.put("pre",pre);
        List<List<Test>>test=new ArrayList<>();
        List<Test>tempTest=new ArrayList<>();
        List<List<List<Flow>>>flow=new ArrayList<>();
        List<Flow>inTempFlow=new ArrayList<>();
        int beginId=0;
        int endId=0;
        int flowBeginId=0;
        int flowEndId=0;
        for(int i=0;i<pre.size();i++){
            beginId=pre.get(i).getBeginId();
            endId=pre.get(i).getEndId();
            tempTest=flowTestService.getTest(beginId,endId);
            test.add(tempTest);
            List<List<Flow>>outTempFlow=new ArrayList<>();
            for(int j=0;j<tempTest.size();j++){
                flowBeginId=tempTest.get(j).getFlowBeginId();
                flowEndId=tempTest.get(j).getFlowEndId();
                inTempFlow=flowTestService.getFlow(flowBeginId,flowEndId);
                outTempFlow.add(inTempFlow);
            }
            flow.add(outTempFlow);
        }
        map.put("test",test);
        map.put("flow",flow);
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestBody HashMap<String,String> parasmap) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        String username=parasmap.get("username");
        int beginId=0;
        int endId=0;
        TestPre testPre=new TestPre();
        for(int i=0;i<date.length;i++){
            testPre=flowTestService.getTestPreByDate(date[i],username);
            beginId=testPre.getBeginId();
            endId=testPre.getEndId();
            List<Test>tests=flowTestService.getTest(beginId,endId);
            for(int j=0;j<tests.size();j++){
                int flowBeginId=tests.get(j).getFlowBeginId();
                int flowEndId=tests.get(j).getFlowEndId();
                flowTestService.removeFlow(flowBeginId,flowEndId);
            }
            flowTestService.removeTest(beginId,endId);
        }
        flowTestService.removeTestPre(date,username);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(String username) {
       TestPre testPre=new TestPre();
       List<TempTest>tempTests=tempFlowTestService.getTest(username);
       for(int i=0;i<tempTests.size();i++){
           int flowBeginId=tempTests.get(i).getFlowBeginId();
           int flowEndId=tempTests.get(i).getFlowEndId();
           List<TempFlow>tempFlows=tempFlowTestService.getFlow(flowBeginId,flowEndId);
           flowTestService.insertFlow(tempFlows);
           int last=flowTestService.getLastIdFlow();
           int count=tempFlows.size();
           int first=last-count+1;
           tempTests.get(i).setFlowBeginId(first);
           tempTests.get(i).setFlowEndId(last);
       }
       flowTestService.insertTest(tempTests);
       int lastId=flowTestService.getLastIdTest();
       int counts=tempTests.size();
       int firstId=lastId-counts+1;
       String testDate=tempTests.get(0).getTestDate();
       testPre.setUsername(username);
       testPre.setTestDate(testDate);
       testPre.setBeginId(firstId);
       testPre.setEndId(lastId);
       flowTestService.insertTestPre(testPre);
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(@RequestBody HashMap<String,String> parasmap, HttpServletResponse response) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        String username=parasmap.get("username");
        TestPre testPre=new TestPre();
        List<List<Test>>test=new ArrayList<>();
        List<Test>tempTest=new ArrayList<>();
        List<List<List<Flow>>>flow=new ArrayList<>();
        List<Flow>inTempFlow=new ArrayList<>();
        int beginId=0;
        int endId=0;
        int flowBeginId=0;
        int flowEndId=0;
        for(int i=0;i<date.length;i++){
            testPre=flowTestService.getTestPreByDate(date[i],username);
            beginId=testPre.getBeginId();
            endId=testPre.getEndId();
            tempTest=flowTestService.getTest(beginId,endId);
            test.add(tempTest);
            List<List<Flow>>outTempFlow=new ArrayList<>();
            for(int j=0;j<tempTest.size();j++){
                flowBeginId=tempTest.get(j).getFlowBeginId();
                flowEndId=tempTest.get(j).getFlowEndId();
                inTempFlow=flowTestService.getFlow(flowBeginId,flowEndId);
                outTempFlow.add(inTempFlow);
            }
            flow.add(outTempFlow);
        }

        String []info=new String[3];
        Patient patient = patientService.findByUsername(username);
        info[0]= patient.getName();
        info[1]= patient.getSex();
        info[2]= patient.getAge();
        POIUtil.exportTest(info,date,test,flow,response);
    }

    @RequestMapping(value = "/findByDate", method = RequestMethod.POST)
    public Map<String, String> findByDate(String testDate,String username) {
        Map<String, String> map = new HashMap<String, String>();
        if (flowTestService.getTestPreByDate(testDate,username)!=null) {
            map.put("status", "error");
        } else {
            map.put("status", "success");
        }
        return map;
    }
}
