package com.example.demo.service;

import com.example.demo.dao.DoctorDao;
import com.example.demo.domain.Doctor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    public List<Doctor> queryPage(Integer pageSize, Integer startRows, String name){
        return doctorDao.queryPage(pageSize, startRows, name);
    }

    public int getRowCount(String name){
        return doctorDao.getRowCount(name);
    }

    public List<Doctor> getAllName(String []doctorId){
        return doctorDao.getAllName(doctorId);
    }

    public Doctor findByUsername(String username){
        return doctorDao.findByUsername(username);
    }

    public Doctor findByUser(String username, String password){
        return doctorDao.findByUser(username, password);
    }

    public int insert(Doctor doctor){
        return doctorDao.insert(doctor);
    }

    public int delete(String username){
        return doctorDao.delete(username);
    }

    public int updateO(Doctor doctor){
        return doctorDao.updateO(doctor);
    }

    public int updateP(Doctor doctor){
        return doctorDao.updateP(doctor);
    }
}
