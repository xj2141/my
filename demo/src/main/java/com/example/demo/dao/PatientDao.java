package com.example.demo.dao;

import com.example.demo.domain.Evaluate;
import com.example.demo.domain.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PatientDao {

    public List<Patient> queryPage(Map<String, Object> params);

    public int getRowCount(Map<String, Object> params);

    //通过用户名查找
    public Patient findByUsername(String username);

    //通过用户名和密码查找
    public Patient findByUser(@Param("username")String username, @Param("password")String password);

    public int insert(Patient patient);

    public int delete(String username);

    //修改个人信息
    public int updateO(Patient patient);

    //修改密码
    public int updateP(Patient patient);
}
