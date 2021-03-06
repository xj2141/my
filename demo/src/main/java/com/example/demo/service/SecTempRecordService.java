package com.example.demo.service;

import com.example.demo.dao.SecTempRecordDao;
import com.example.demo.domain.SecTempRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecTempRecordService {

    @Autowired
    private SecTempRecordDao secTempRecordDao;

    public List<SecTempRecord> get(){
        return secTempRecordDao.get();
    }

    public int getCount(){
        return secTempRecordDao.getCount();
    }

    public int insert(SecTempRecord secTempRecord){
        return secTempRecordDao.insert(secTempRecord);
    }

    public int update(SecTempRecord secTempRecord){
        return secTempRecordDao.update(secTempRecord);
    }

    public int delete(int recordId){
        return secTempRecordDao.delete(recordId);
    }

    public int remove(){
        return secTempRecordDao.remove();
    }
}
