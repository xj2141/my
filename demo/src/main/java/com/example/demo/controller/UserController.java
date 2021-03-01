package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
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
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = user.getUsername();
        String password = user.getPassword();
        String target = MD5Util.md5(username + password);
        user.setPassword(target);
        if (userService.findByUser(user) == null) {
            map.put("status", "error");
            map.put("msg", "用户名或密码错误");
        } else {
            map.put("status", "success");
            map.put("msg", "登录成功");
        }
        return map;
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Map<String, Object> regist(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = user.getUsername();
        String password = user.getPassword();

        if (userService.findByUsername(username) != null) {
            map.put("status", "error");
            map.put("msg", "用户名已存在");
        } else {
            String target = MD5Util.md5(username + password);
            user.setPassword(target);
            user.setSex("男");
            userService.insert(user);
            map.put("status", "success");
            map.put("msg", "注册成功");
        }
        return map;
    }

    //修改基本信息
    @RequestMapping(value = "/updateO", method = RequestMethod.POST)
    public Map<String, Object> updateO(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = userService.updateO(user);
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
        User user=userService.findByUsername(username);
        if(user!=null){
            map.put("status", "success");
            map.put("name", user.getName());
            map.put("sex", user.getSex());
            map.put("age", user.getAge());
            map.put("phone", user.getPhone());
        }else{
            map.put("status", "error");
            map.put("msg","获取用户信息失败");
        }
        return map;
    }

    //修改密码
    @RequestMapping(value = "/updateP", method = RequestMethod.POST)
    public Map<String, Object> updateP(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = user.getUsername();
        String password = user.getPassword();
        String target = MD5Util.md5(username + password);
        user.setPassword(target);
        int result = userService.updateP(user);
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
