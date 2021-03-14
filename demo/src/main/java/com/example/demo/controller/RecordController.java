package com.example.demo.controller;

import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import com.example.demo.domain.Patient;
import com.example.demo.service.RecordService;
import com.example.demo.service.TempRecordService;
import com.example.demo.service.PatientService;
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
    private PatientService patientService;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public Map<String, List> getAll(String firstDate, String lastDate, String username) {
        Map<String, List> map = new HashMap<String, List>();
        List<PreRecord>pre=recordService.getPre(firstDate,lastDate,username);
        map.put("pre",pre);
        List<List<SufRecord>>suf=new ArrayList<>();
        List<SufRecord>tempSuf=new ArrayList<>();
        int beginId=0;
        int endId=0;
        for(int i=0;i<pre.size();i++){
            beginId=pre.get(i).getBeginId();
            endId=pre.get(i).getEndId();
            tempSuf=recordService.getSuf(beginId,endId);
            suf.add(tempSuf);
        }
        map.put("suf",suf);
        return map;
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public void deleteAll(@RequestBody HashMap<String,String> parasmap) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        String username=parasmap.get("username");
        int beginId=0;
        int endId=0;
        for(int i=0;i<date.length;i++){
            beginId=recordService.getPreByDate(date[i],username).getBeginId();
            endId=recordService.getPreByDate(date[i],username).getEndId();
            recordService.removeSuf(beginId,endId);
        }
        recordService.removePre(date,username);
    }

    @RequestMapping(value = "/exportAll", method = RequestMethod.POST)
    public void exportAll(@RequestBody HashMap<String,String> parasmap, HttpServletResponse response) {
        String dates=parasmap.get("dates");
        String []date=dates.split(",");
        String username=parasmap.get("username");
        List<PreRecord>pre=new ArrayList<>();
        PreRecord tempPre=new PreRecord();
        List<List<SufRecord>>suf=new ArrayList<>();
        List<SufRecord>tempSuf=new ArrayList<>();
        int beginId=0;
        int endId=0;
        for(int i=0;i<date.length;i++){
            tempPre=recordService.getPreByDate(date[i],username);
            pre.add(tempPre);
            beginId=tempPre.getBeginId();
            endId=tempPre.getEndId();
            tempSuf=recordService.getSuf(beginId,endId);
            suf.add(tempSuf);
        }
        String []info=new String[3];
        Patient patient = patientService.findByUsername(username);
        info[0]= patient.getName();
        info[1]= patient.getSex();
        info[2]= patient.getAge();
        POIUtil.exportRecord(info,pre,suf,response);
    }

    @RequestMapping(value = "/findByDate", method = RequestMethod.POST)
    public Map<String, Object> findByDate(String recordDate,String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (recordService.getPreByDate(recordDate,username) != null) {
            map.put("status", "error");
        } else {
            map.put("status", "success");
        }
        return map;
    }

    @RequestMapping(value = "/insertTempPre", method = RequestMethod.POST)
    public int insertTempPre(PreRecord preRecord){
        String username=preRecord.getUsername();
        int count=tempRecordService.getCountSuf(username);
        int last=recordService.getLastIdSuf();
        int first=last-count+1;
        preRecord.setBeginId(first);
        preRecord.setEndId(last);
        int result= recordService.insertPre(preRecord);
        return result;
    }

    @RequestMapping(value = "/insertTempSuf", method = RequestMethod.POST)
    public int insertTempSuf(String username){
        int result= recordService.insertTempSuf(username);
        return result;
    }
}
