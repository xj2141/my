package com.example.demo.service;

import com.example.demo.dao.FlowTestDao;
import com.example.demo.dao.TempFlowTestDao;
import com.example.demo.domain.Flow;
import com.example.demo.domain.TempFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowTestService {

    @Autowired
    private FlowTestDao flowTestDao;

    public List<Flow> getFlow(Integer beginId, Integer endId){
        return flowTestDao.getFlow(beginId,endId);
    }

    public int insertFlow(List<Flow>flows){
        return flowTestDao.insertFlow(flows);
    }

    public int deleteFlow(Integer beginId, Integer endId){
        return flowTestDao.deleteFlow(beginId, endId);
    }

    public int removeFlow(){
        return flowTestDao.removeFlow();
    }
}
