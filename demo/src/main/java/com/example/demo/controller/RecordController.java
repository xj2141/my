package com.example.demo.controller;

import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import com.example.demo.service.RecordService;
import com.example.demo.service.TempRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private TempRecordService tempRecordService;

    @RequestMapping(value = "/getPre", method = RequestMethod.POST)
    public List<PreRecord> getPre(){
        return recordService.getPre();
    }

    @RequestMapping(value = "/findByDate", method = RequestMethod.POST)
    public Map<String, Object> findByDate(String recordDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (recordService.getPreByDate(recordDate) != null) {
            map.put("status", "error");
        } else {
            map.put("status", "success");
        }
        return map;
    }

    @RequestMapping(value = "/insertPre", method = RequestMethod.POST)
    public int insertPre(PreRecord preRecord){
        int count=tempRecordService.getCountSuf();
        System.out.println(count);
        int last=recordService.getLastIdSuf();
        int first=last-count+1;
        preRecord.setBeginId(first);
        preRecord.setEndId(last);
        int result= recordService.insertPre(preRecord);
        return result;
    }

    @RequestMapping(value = "/removePre", method = RequestMethod.POST)
    public int removePre(@RequestBody HashMap<String,String> parasmap){
        String ids=parasmap.get("dates");
        String []date=ids.split(",");
        int result= recordService.removePre(date);
        return result;
    }

    @RequestMapping(value = "/getSuf", method = RequestMethod.POST)
    public List<SufRecord> getSuf(Integer beginId, Integer endId){
        return recordService.getSuf(beginId,endId);
    }

    @RequestMapping(value = "/insertTempSuf", method = RequestMethod.POST)
    public int insertTempSuf(){
        int result= recordService.insertTempSuf();
        return result;
    }

    @RequestMapping(value = "/removeSuf", method = RequestMethod.POST)
    public int removeSuf(@RequestBody HashMap<String,String> parasmap){
        String ids=parasmap.get("ids");
        String []id=ids.split(",");
        int result= recordService.removeSuf(id);
        return result;
    }
}
