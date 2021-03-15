package com.example.demo.dao;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    public List<User> queryPage(@Param("pageSize")Integer pageSize, @Param("startRows")Integer startRows, @Param("username") String username, @Param("state") String state);

    public int getRowCount(@Param("username") String username, @Param("state") String state);

    public User findByUsername(String username);

    public int updateOne(User user);

    public int insert(List<User>users);

    public int update(Map<String, Object> params);

    public int delete(String []username);
}
