package com.example.demo.controller;

import com.example.demo.domain.Doctor;
import com.example.demo.service.DoctorService;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public List<Doctor> page(Integer page, Integer pageSize, String name){
        int pageNow = page == null ? 1 : page;
        int startRows = pageSize*(pageNow-1);
        List<Doctor> list= doctorService.queryPage(pageSize,startRows,name);
        return list;
    }

    @RequestMapping(value = "/rows", method = RequestMethod.POST)
    @ResponseBody
    public int rows(String name){
        int re=doctorService.getRowCount(name);
        return re;
    }

    @RequestMapping(value = "/getAllName", method = RequestMethod.POST)
    @ResponseBody
    public String getAllName(@RequestBody HashMap<String,String> parasmap){
        String ids=parasmap.get("ids");
        String []doctorId=ids.split(",");
        List<Doctor>doctors=doctorService.getAllName(doctorId);
        String doctorName="";
        int length=doctors.size();
        for(int i=0;i<length;i++){
            doctorName+=doctors.get(i).getName();
            if(i!=length-1){
                doctorName+="，";
            }
        }
        return doctorName;
    }

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Map<String, Object> findByUser(String username,String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doctorService.findByUser(username, password)!=null) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map<String, Object> insert(Doctor doctor) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = doctorService.insert(doctor);
        if (result >= 1) {
            map.put("status", "success");
            map.put("msg", "添加成功");
        } else {
            map.put("status", "error");
            map.put("msg", "添加失败");
        }
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Map<String, Object> delete(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = doctorService.delete(username);
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
    public Map<String, Object> updateO(Doctor doctor) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = doctorService.updateO(doctor);
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
        Doctor doctor=doctorService.findByUsername(username);
        if(doctor!=null){
            map.put("status", "success");
            map.put("name", doctor.getName());
            map.put("sex", doctor.getSex());
            map.put("age", doctor.getAge());
            map.put("phone", doctor.getPhone());
            map.put("hospital", doctor.getHospital());
            map.put("department", doctor.getDepartment());
            map.put("professionTitle", doctor.getProfessionTitle());
            map.put("profession", doctor.getProfession());
        }else{
            map.put("status", "error");
            map.put("msg","获取用户信息失败");
        }
        return map;
    }

    //修改密码
    @RequestMapping(value = "/updateP", method = RequestMethod.POST)
    public Map<String, Object> updateP(Doctor doctor) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = doctorService.updateP(doctor);
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
