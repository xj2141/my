package com.example.demo.service;

import com.example.demo.dao.FlowTestDao;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowTestService {

    @Autowired
    private FlowTestDao flowTestDao;

    //尿流检测前缀
    public List<TestPre> getTestPre(String firstDate, String lastDate, String username){
        return flowTestDao.getTestPre(firstDate,lastDate,username);
    }

    public TestPre getTestPreByDate(String testDate, String username){
        return flowTestDao.getTestPreByDate(testDate,username);
    }

    public int insertTestPre(TestPre testPre){
        return flowTestDao.insertTestPre(testPre);
    }

    public int removeTestPre(String []testDate, String username){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dates", testDate);
        params.put("username", username);
        return flowTestDao.removeTestPre(params);
    }

    //尿流检测
    public List<Test> getTest(Integer beginId, Integer endId){
        return flowTestDao.getTest(beginId, endId);
    }

    public int getLastIdTest(){
        return flowTestDao.getLastIdTest();
    }

    public int insertTest(List<TempTest>tests){
        return flowTestDao.insertTest(tests);
    }

    public int removeTest(Integer beginId,Integer endId){
        return flowTestDao.removeTest(beginId, endId);
    }

    //尿流率数据
    public List<Flow> getFlow(Integer beginId, Integer endId){
        return flowTestDao.getFlow(beginId, endId);
    }

    public int insertFlow(List<TempFlow>flows){
        return flowTestDao.insertFlow(flows);
    }

    public int removeFlow(Integer beginId, Integer endId){
        return flowTestDao.removeFlow(beginId, endId);
    }

    public int getLastIdFlow(){
        return flowTestDao.getLastIdFlow();
    }
}
