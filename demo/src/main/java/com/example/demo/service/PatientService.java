package com.example.demo.service;

import com.example.demo.dao.PatientDao;
import com.example.demo.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {
    @Autowired
    private PatientDao patientDao;

    public List<Patient> queryPage(Integer pageSize, Integer startRows, String name, String []patientId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageSize",pageSize);
        params.put("startRows",startRows);
        params.put("name",name);
        params.put("patients",patientId);
        return patientDao.queryPage(params);
    }

    public int getRowCount(String name,String []patientId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name",name);
        params.put("patients",patientId);
        return patientDao.getRowCount(params);
    }

    public Patient findByUsername(String username){
        return patientDao.findByUsername(username);
    }

    public Patient findByUser(String username, String password){
        return patientDao.findByUser(username, password);
    }

    public int insert(Patient patient){
        return patientDao.insert(patient);
    }

    public int delete(String username){
        return patientDao.delete(username);
    }

    public int updateO(Patient patient){
        return patientDao.updateO(patient);
    }

    public int updateP(Patient patient){
        return patientDao.updateP(patient);
    }

}
