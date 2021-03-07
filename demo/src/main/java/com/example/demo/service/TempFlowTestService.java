package com.example.demo.service;

import com.example.demo.dao.TempFlowTestDao;
import com.example.demo.domain.TempFlow;
import com.example.demo.domain.TempTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempFlowTestService {

    @Autowired
    private TempFlowTestDao tempFlowTestDao;

    public int updateDate(String testDate){
        return tempFlowTestDao.updateDate(testDate);
    }

    public List<TempTest> getTest(){
        return tempFlowTestDao.getTest();
    }

    public int getCountTest(){
        return tempFlowTestDao.getCountTest();
    }

    public int insertTest(TempTest tempTest){
        return tempFlowTestDao.insertTest(tempTest);
    }

    public int updateTest(TempTest tempTest){
        return tempFlowTestDao.updateTest(tempTest);
    }

    public int deleteTest(int testId){
        return tempFlowTestDao.deleteTest(testId);
    }

    public int removeTest(){
        return tempFlowTestDao.removeTest();
    }

    public List<TempFlow> getFlow(Integer beginId, Integer endId){
        return tempFlowTestDao.getFlow(beginId,endId);
    }

    public int getLastIdFlow(){
        return tempFlowTestDao.getLastIdFlow();
    }

    public int insertFlow(List<TempFlow>tempFlows){
        return tempFlowTestDao.insertFlow(tempFlows);
    }

    public int deleteFlow(Integer beginId, Integer endId){
        return tempFlowTestDao.deleteFlow(beginId, endId);
    }

    public int removeFlow(){
        return tempFlowTestDao.removeFlow();
    }
}
