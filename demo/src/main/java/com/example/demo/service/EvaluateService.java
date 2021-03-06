package com.example.demo.service;

import com.example.demo.dao.EvaluateDao;
import com.example.demo.domain.Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluateService {

    @Autowired
    private EvaluateDao evaluateDao;

    public Evaluate findByEvaluate(Evaluate evaluate){
        return evaluateDao.findByEvaluate(evaluate);
    }

    public List<Evaluate> queryPage(Integer pageSize, Integer startRows, String evaluateName, String username){
        return evaluateDao.queryPage(pageSize,startRows,evaluateName,username);
    }

    public int getRowCount(String evaluateName, String username){
        return evaluateDao.getRowCount(evaluateName,username);
    }

    public int insert(Evaluate evaluate){
        return evaluateDao.insert(evaluate);
    }

    public int remove(String []id){
        return evaluateDao.remove(id);
    }

    public int update(Evaluate evaluate){
        return evaluateDao.update(evaluate);
    }
}
