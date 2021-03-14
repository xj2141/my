package com.example.demo.controller;

import com.example.demo.domain.Connection;
import com.example.demo.domain.Patient;
import com.example.demo.service.ConnectionService;
import com.example.demo.service.PatientService;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public List<Patient> page(Integer page, Integer pageSize, String name,String doctorId){
        List<Connection>connections=connectionService.getByDoctor(doctorId);
        int length=connections.size();
        if(length==0){
            List<Patient>result=new ArrayList<>();
            return result;
        }else{
            String [] patientId=new String[length];
            for(int i=0;i<length;i++){
                patientId[i]=connections.get(i).getPatientId();
            }
            int pageNow = page == null ? 1 : page;
            int startRows = pageSize*(pageNow-1);
            List<Patient> list= patientService.queryPage(pageSize,startRows,name,patientId);
            return list;
        }
    }

    @RequestMapping(value = "/rows", method = RequestMethod.POST)
    @ResponseBody
    public int rows(String name,String doctorId){
        List<Connection>connections=connectionService.getByDoctor(doctorId);
        int length=connections.size();
        if(length==0){
            return 0;
        }else{
            String [] patientId=new String[length];
            for(int i=0;i<length;i++){
                patientId[i]=connections.get(i).getPatientId();
            }
            int re=patientService.getRowCount(name,patientId);
            return re;
        }
    }

    @RequestMapping(value = "/findByUsername", method = RequestMethod.POST)
    public Map<String, Object> findByUsername(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (patientService.findByUsername(username)!=null) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Map<String, Object> findByUser(String username,String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (patientService.findByUser(username, password)!=null) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> delete(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = patientService.delete(username);
        if (result >= 1) {
            map.put("status", "success");
            map.put("msg", "注销成功");
        } else {
            map.put("status", "error");
            map.put("msg", "注销失败");
        }
        return map;
    }

    //修改基本信息
    @RequestMapping(value = "/updateO", method = RequestMethod.POST)
    public Map<String, Object> updateO(Patient patient) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = patientService.updateO(patient);
        if (result >= 1) {
            map.put("status", "success");
            map.put("msg", "修改成功");
        } else {
            map.put("status", "error");
            map.put("msg", "修改失败");
        }
        return map;
    }

    //获取基本信息
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public Map<String, Object> getInfo(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        Patient patient = patientService.findByUsername(username);
        if(patient !=null){
            map.put("status", "success");
            map.put("name", patient.getName());
            map.put("sex", patient.getSex());
            map.put("age", patient.getAge());
            map.put("phone", patient.getPhone());
        }else{
            map.put("status", "error");
            map.put("msg","获取用户信息失败");
        }
        return map;
    }

    //修改密码
    @RequestMapping(value = "/updateP", method = RequestMethod.POST)
    public Map<String, Object> updateP(Patient patient) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = patient.getUsername();
        String password = patient.getPassword();
        String target = MD5Util.md5(username + password);
        patient.setPassword(target);
        int result = patientService.updateP(patient);
        if (result >= 1) {
            map.put("status", "success");
            map.put("msg", "修改成功");
        } else {
            map.put("status", "error");
            map.put("msg", "修改失败");
        }
        return map;
    }
}
