package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.User;
import com.example.demo.service.ConnectionService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import com.example.demo.service.UserService;
import com.example.demo.utils.GenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public List<User> page(Integer page, Integer pageSize, String username, String state){
        int pageNow = page == null ? 1 : page;
        int startRows = pageSize*(pageNow-1);
        List<User> list= userService.queryPage(pageSize,startRows,username,state);
        return list;
    }

    @RequestMapping(value = "/rows", method = RequestMethod.POST)
    @ResponseBody
    public int rows(String username,String state){
        int re=userService.getRowCount(username,state);
        return re;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(String number){
        int num=Integer.parseInt(number);
        List<User>users=new ArrayList<>();
        for(int i=0;i<num;i++){
            String username= GenerateUtil.generateUserName();
            while(username.equals("admin")||patientService.findByUsername(username)!=null||userService.findByUsername(username)!=null){
                username=GenerateUtil.generateUserName();
            }
            String password=GenerateUtil.generatePassword();
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setState("未使用");
            users.add(user);
        }
        userService.insert(users);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(@RequestBody HashMap<String,String> parasmap) {
        String params=parasmap.get("users");
        JSONObject jb = JSONObject.parseObject(params);
        List<User> users = JSONObject.parseArray(jb.getJSONArray("params").toJSONString(), User.class);
        String op=parasmap.get("op");
        Doctor doctor=new Doctor();
        int length=users.size();
        String []username=new String[length];
        if(op.equals("启用")){
            for(int i=0;i<length;i++){
                User user=users.get(i);
                username[i]=user.getUsername();
                if(user.getState().equals("已禁用")){
                    doctor.setUsername(user.getUsername());
                    doctor.setPassword(user.getPassword());
                    doctor.setName(user.getName());
                    doctor.setSex(user.getSex());
                    doctor.setAge(user.getAge());
                    doctor.setPhone(user.getPhone());
                    doctor.setHospital(user.getHospital());
                    doctor.setDepartment(user.getDepartment());
                    doctor.setProfessionTitle(user.getProfessionTitle());
                    doctor.setProfession(user.getProfession());
                    doctorService.insert(doctor);
                }else if(user.getState().equals("未使用")){
                    doctor.setUsername(user.getUsername());
                    doctor.setPassword(user.getPassword());
                    doctorService.insert(doctor);
                }
            }
            userService.update(username,"已启用");
        }else if(op.equals("禁用")){
            for(int i=0;i<length;i++){
                User user=users.get(i);
                username[i]=user.getUsername();
                if(user.getState().equals("已启用")){
                    doctor=doctorService.findByUsername(user.getUsername());
                    doctorService.delete(doctor.getUsername());
                    User tempUser=new User();
                    tempUser.setUsername(doctor.getUsername());
                    tempUser.setPassword(doctor.getPassword());
                    tempUser.setName(doctor.getName());
                    tempUser.setSex(doctor.getSex());
                    tempUser.setAge(doctor.getAge());
                    tempUser.setPhone(doctor.getPhone());
                    tempUser.setHospital(doctor.getHospital());
                    tempUser.setDepartment(doctor.getDepartment());
                    tempUser.setProfessionTitle(doctor.getProfessionTitle());
                    tempUser.setProfession(doctor.getProfession());
                    userService.updateOne(tempUser);
                }
            }
            userService.update(username,"已禁用");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(@RequestBody HashMap<String,String> parasmap) {
        String usernames=parasmap.get("usernames");
        String []username=usernames.split(",");
        String states=parasmap.get("states");
        String []state=states.split(",");
        for(int i=0;i<state.length;i++){
            if(state[i].equals("已启用")){
                doctorService.delete(username[i]);
            }
            connectionService.deleteByDoctor(username[i]);
        }
        userService.delete(username);
    }
}
