package com.example.demo.service;

import com.example.demo.dao.RecordDao;
import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordDao recordDao;

    /*日志前缀*/
    public List<PreRecord> getPre(String firstDate, String lastDate){
        return recordDao.getPre(firstDate, lastDate);
    }

    public PreRecord getPreByDate(String recordDate){
        return recordDao.getPreByDate(recordDate);
    }

    public int insertPre(PreRecord preRecord){
        return recordDao.insertPre(preRecord);
    }

    public int removePre(String []recordDate){
        return recordDao.removePre(recordDate);
    }

    /*日志后缀*/
    public List<SufRecord> getSuf(Integer beginId,Integer endId){
        return recordDao.getSuf(beginId,endId);
    }

    public int getLastIdSuf(){
        return recordDao.getLastIdSuf();
    }

    public int insertTempSuf(){
        return recordDao.insertTempSuf();
    }

    public int insertSuf(){
        return recordDao.insertSuf();
    }

    public int removeSuf(Integer beginId,Integer endId){
        return recordDao.removeSuf(beginId,endId);
    }
}
