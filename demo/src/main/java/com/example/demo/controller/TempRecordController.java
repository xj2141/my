package com.example.demo.controller;

import com.example.demo.domain.TempPreRecord;
import com.example.demo.domain.TempSufRecord;
import com.example.demo.service.TempRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tempRecord")
public class TempRecordController {

    @Autowired
    private TempRecordService tempRecordService;

    @RequestMapping(value = "/getPre", method = RequestMethod.POST)
    public List<TempPreRecord> getPre(String username){
        return tempRecordService.getPre(username);
    }

    @RequestMapping(value = "/insertPre", method = RequestMethod.POST)
    public int insertPre(TempPreRecord tempPreRecord){
        int result= tempRecordService.insertPre(tempPreRecord);
        return result;
    }

    @RequestMapping(value = "/updatePre", method = RequestMethod.POST)
    public int updatePre(TempPreRecord tempPreRecord){
        int result= tempRecordService.updatePre(tempPreRecord);
        return result;
    }

    @RequestMapping(value = "/removePre", method = RequestMethod.POST)
    public int removePre(String username){
        int result= tempRecordService.removePre(username);
        return result;
    }

    @RequestMapping(value = "/getSuf", method = RequestMethod.POST)
    public List<TempSufRecord> getSuf(String username){
        System.out.println(username);
        return tempRecordService.getSuf(username);
    }

    @RequestMapping(value = "/getCountSuf", method = RequestMethod.POST)
    public int getCountSuf(String username){
        return tempRecordService.getCountSuf(username);
    }

    @RequestMapping(value = "/insertSuf", method = RequestMethod.POST)
    public int insertSuf(TempSufRecord tempSufRecord){
        int result= tempRecordService.insertSuf(tempSufRecord);
        return result;
    }

    @RequestMapping(value = "/updateSuf", method = RequestMethod.POST)
    public int updateSuf(TempSufRecord tempSufRecord){
        System.out.println(tempSufRecord.toString());
        int result= tempRecordService.updateSuf(tempSufRecord);
        return result;
    }

    @RequestMapping(value = "/deleteSuf", method = RequestMethod.POST)
    public int deleteSuf(int recordId){
        int result= tempRecordService.deleteSuf(recordId);
        return result;
    }

    @RequestMapping(value = "/removeSuf", method = RequestMethod.POST)
    public int removeSuf(String username){
        int result= tempRecordService.removeSuf(username);
        return result;
    }
}
