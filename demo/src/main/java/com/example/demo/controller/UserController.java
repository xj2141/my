package com.example.demo.controller;

import com.example.demo.domain.Doctor;
import com.example.demo.domain.Patient;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(username.equals("admin")){
            if(password.equals("@xjj1115")){
                map.put("status", "success");
                map.put("role","admin");
            }else{
                map.put("status", "error");
            }
        }else{
            String target = MD5Util.md5(username + password);
            Doctor doctor=doctorService.findByUser(username,password);
            Patient patient=patientService.findByUser(username,target);
            if (doctor == null&&patient==null) {
                map.put("status", "error");
            } else {
                map.put("status", "success");
                if(doctor!=null){
                    map.put("role","doctor");
                }else{
                    map.put("role","patient");
                }
            }
        }
        return map;
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Map<String, Object> regist(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (username.equals("admin")||doctorService.findByUsername(username)!=null||patientService.findByUsername(username) != null) {
            map.put("status", "error");
            map.put("msg", "用户名已存在");
        } else {
            String target = MD5Util.md5(username + password);
            Patient patient=new Patient();
            patient.setUsername(username);
            patient.setPassword(target);
            patient.setSex("男");
            patientService.insert(patient);
            map.put("status", "success");
            map.put("msg", "注册成功");
        }
        return map;
    }
}
