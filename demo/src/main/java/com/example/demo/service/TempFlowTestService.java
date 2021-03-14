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

    public int updateDate(String username,String testDate){
        return tempFlowTestDao.updateDate(username,testDate);
    }

    public List<TempTest> getTest(String username){
        return tempFlowTestDao.getTest(username);
    }

    public int getCountTest(String username){
        return tempFlowTestDao.getCountTest(username);
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

    public int removeTest(String username){
        return tempFlowTestDao.removeTest(username);
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
}
