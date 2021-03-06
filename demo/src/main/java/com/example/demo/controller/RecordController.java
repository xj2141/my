package com.example.demo.controller;

import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import com.example.demo.domain.User;
import com.example.demo.service.RecordService;
import com.example.demo.service.SecTempRecordService;
import com.example.demo.service.TempRecordService;
import com.example.demo.service.UserService;
import com.example.demo.utils.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @Autowired
    private SecTempRecordService secTempRecordService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Map<String, List> getAll(String firstDate, String lastDate) {
        Map<String, List> map = new HashMap<String, List>();
        List<PreRecord>pre=this.getPre(firstDate,lastDate);
        map.put("pre",pre);
        List<List<SufRecord>>suf=new ArrayList<>();
        List<SufRecord>tempSuf=new ArrayList<>();
        int beginId=0;
        int endId=0;
        for(int i=0;i<pre.size();i++){
            beginId=pre.get(i).getBeginId();
            endId=pre.get(i).getEndId();
            tempSuf=this.getSuf(beginId,endId);
            suf.add(tempSuf);
        }
        map.put("suf",suf);
        return map;
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public void deleteAll(@RequestBody HashMap<String,String> parasmap) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        int beginId=0;
        int endId=0;
        for(int i=0;i<date.length;i++){
            beginId=recordService.getPreByDate(date[i]).getBeginId();
            endId=recordService.getPreByDate(date[i]).getEndId();
            this.removeSuf(beginId,endId);
        }
        this.removePre(date);
    }

    @RequestMapping(value = "/exportAll", method = RequestMethod.POST)
    public void exportAll(@RequestBody HashMap<String,String> parasmap, HttpServletResponse response) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        List<PreRecord>pre=new ArrayList<>();
        PreRecord tempPre=new PreRecord();
        List<List<SufRecord>>suf=new ArrayList<>();
        List<SufRecord>tempSuf=new ArrayList<>();
        int beginId=0;
        int endId=0;
        for(int i=0;i<date.length;i++){
            tempPre=recordService.getPreByDate(date[i]);
            pre.add(tempPre);
            beginId=tempPre.getBeginId();
            endId=tempPre.getEndId();
            tempSuf=this.getSuf(beginId,endId);
            suf.add(tempSuf);
        }
        String username=parasmap.get("username");
        String []info=new String[3];
        User user=userService.findByUsername(username);
        info[0]=user.getName();
        info[1]=user.getSex();
        info[2]=user.getAge();
        POIUtil.exportRecord(info,pre,suf,response);
    }

    @RequestMapping(value = "/getPre", method = RequestMethod.POST)
    public List<PreRecord> getPre(String firstDate, String lastDate){
        return recordService.getPre(firstDate,lastDate);
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

    @RequestMapping(value = "/insertTempPre", method = RequestMethod.POST)
    public int insertTempPre(PreRecord preRecord){
        int count=tempRecordService.getCountSuf();
        System.out.println(count);
        int last=recordService.getLastIdSuf();
        int first=last-count+1;
        preRecord.setBeginId(first);
        preRecord.setEndId(last);
        int result= recordService.insertPre(preRecord);
        return result;
    }

    @RequestMapping(value = "/insertPre", method = RequestMethod.POST)
    public int insertPre(PreRecord preRecord){
        int count=secTempRecordService.getCount();
        System.out.println(count);
        int last=recordService.getLastIdSuf();
        int first=last-count+1;
        preRecord.setBeginId(first);
        preRecord.setEndId(last);
        int result= recordService.insertPre(preRecord);
        return result;
    }

    @RequestMapping(value = "/removePre", method = RequestMethod.POST)
    public int removePre(String []date){
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

    @RequestMapping(value = "/insertSuf", method = RequestMethod.POST)
    public int insertSuf(){
        int result= recordService.insertSuf();
        return result;
    }

    @RequestMapping(value = "/removeSuf", method = RequestMethod.POST)
    public int removeSuf(Integer beginId, Integer endId){
        int result= recordService.removeSuf(beginId,endId);
        return result;
    }
}
