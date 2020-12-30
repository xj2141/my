package com.example.demo.dao;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    public User findByUsername(String username);

    public User findByUser(User user);

    public int insert(User user);

    public int updateO(User user);

    public int updateP(User user);
}
