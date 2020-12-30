package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public User findByUser(User user){
        return userDao.findByUser(user);
    }

    public int insert(User user){
        return userDao.insert(user);
    }

    public int updateO(User user){
        return userDao.updateO(user);
    }

    public int updateP(User user){
        return userDao.updateP(user);
    }

}
