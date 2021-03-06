package com.example.demo.controller;

import com.example.demo.domain.SecTempRecord;
import com.example.demo.service.SecTempRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/secTempRecord")
public class SecTempRecordController {
    
    @Autowired
    private SecTempRecordService secTempRecordService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<SecTempRecord> get(){
        return secTempRecordService.get();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(SecTempRecord secTempRecord){
        int result= secTempRecordService.insert(secTempRecord);
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(SecTempRecord secTempRecord){
        int result= secTempRecordService.update(secTempRecord);
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(int recordId){
        int result= secTempRecordService.delete(recordId);
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public int remove(){
        int result= secTempRecordService.remove();
        return result;
    }
}
