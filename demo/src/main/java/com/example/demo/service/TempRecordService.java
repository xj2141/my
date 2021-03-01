package com.example.demo.service;

import com.example.demo.dao.TempRecordDao;
import com.example.demo.domain.TempPreRecord;
import com.example.demo.domain.TempSufRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempRecordService {

    @Autowired
    private TempRecordDao tempRecordDao;

    public List<TempPreRecord> getPre(){
        return tempRecordDao.getPre();
    }

    public int insertPre(TempPreRecord tempPreRecord){
        return tempRecordDao.insertPre(tempPreRecord);
    }

    public int updatePre(TempPreRecord tempPreRecord){
        return tempRecordDao.updatePre(tempPreRecord);
    }

    public int removePre(){
        return tempRecordDao.removePre();
    }

    public List<TempSufRecord> getSuf(){
        return tempRecordDao.getSuf();
    }

    public int getCountSuf(){
        return tempRecordDao.getCountSuf();
    }

    public int insertSuf(TempSufRecord tempSufRecord){
        return tempRecordDao.insertSuf(tempSufRecord);
    }

    public int updateSuf(TempSufRecord tempSufRecord){
        return tempRecordDao.updateSuf(tempSufRecord);
    }

    public int deleteSuf(int recordId){
        return tempRecordDao.deleteSuf(recordId);
    }

    public int removeSuf(){
        return tempRecordDao.removeSuf();
    }
}
