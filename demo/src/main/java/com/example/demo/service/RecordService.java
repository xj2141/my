package com.example.demo.service;

import com.example.demo.dao.RecordDao;
import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {

    @Autowired
    private RecordDao recordDao;

    /*日志前缀*/
    public List<PreRecord> getPre(String firstDate, String lastDate, String username){
        return recordDao.getPre(firstDate, lastDate,username);
    }

    public PreRecord getPreByDate(String recordDate,String username){
        return recordDao.getPreByDate(recordDate,username);
    }

    public int insertPre(PreRecord preRecord){
        return recordDao.insertPre(preRecord);
    }

    public int removePre(String []recordDate, String username){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dates", recordDate);
        params.put("username", username);
        return recordDao.removePre(params);
    }

    /*日志后缀*/
    public List<SufRecord> getSuf(Integer beginId,Integer endId){
        return recordDao.getSuf(beginId,endId);
    }

    public int getLastIdSuf(){
        return recordDao.getLastIdSuf();
    }

    public int insertTempSuf(String username){
        return recordDao.insertTempSuf(username);
    }

    public int removeSuf(Integer beginId,Integer endId){
        return recordDao.removeSuf(beginId,endId);
    }
}
