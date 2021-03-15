package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> queryPage(Integer pageSize, Integer startRows, String username, String state){
        return userDao.queryPage(pageSize, startRows, username, state);
    }

    public int getRowCount(String username, String state){
        return userDao.getRowCount(username, state);
    }

    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public int updateOne(User user){
        return userDao.updateOne(user);
    }

    public int insert(List<User>users){
        return userDao.insert(users);
    }

    public int update(String []username, String state){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("usernames", username);
        params.put("state", state);
        return userDao.update(params);
    }

    public int delete(String []username){
        return userDao.delete(username);
    }
}
