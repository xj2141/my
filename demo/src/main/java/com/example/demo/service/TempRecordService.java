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

    public List<TempPreRecord> getPre(String username){
        return tempRecordDao.getPre(username);
    }

    public int insertPre(TempPreRecord tempPreRecord){
        return tempRecordDao.insertPre(tempPreRecord);
    }

    public int updatePre(TempPreRecord tempPreRecord){
        return tempRecordDao.updatePre(tempPreRecord);
    }

    public int removePre(String username){
        return tempRecordDao.removePre(username);
    }

    public List<TempSufRecord> getSuf(String username){
        return tempRecordDao.getSuf(username);
    }

    public int getCountSuf(String username){
        return tempRecordDao.getCountSuf(username);
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

    public int removeSuf(String username){
        return tempRecordDao.removeSuf(username);
    }
}
