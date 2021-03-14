package com.example.demo.dao;

import com.example.demo.domain.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorDao {

    public List<Doctor> queryPage(@Param("pageSize")Integer pageSize, @Param("startRows")Integer startRows, @Param("name") String name);

    public int getRowCount(@Param("name") String name);

    public List<Doctor> getAllName(String []doctorId);

    //通过用户名查找
    public Doctor findByUsername(String username);

    //通过用户名和密码查找
    public Doctor findByUser(@Param("username")String username, @Param("password")String password);

    public int insert(Doctor doctor);

    public int delete(String username);

    //修改个人信息
    public int updateO(Doctor doctor);

    //修改密码
    public int updateP(Doctor doctor);
}
