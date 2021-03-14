package com.example.demo.service;

import com.example.demo.dao.ConnectionDao;
import com.example.demo.domain.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionDao connectionDao;

    public List<Connection> getByDoctor(String doctorId){
        return connectionDao.getByDoctor(doctorId);
    }

    public List<Connection> findByPatient(String patientId){
        return connectionDao.findByPatient(patientId);
    }

    public Connection findByConnection(Connection connection){
        return connectionDao.findByConnection(connection);
    }

    public int insert(Connection connection){
        return connectionDao.insert(connection);
    }

    public int update(Connection connection){
        return connectionDao.update(connection);
    }

    public int deleteByDoctor(String doctorId){
        return connectionDao.deleteByDoctor(doctorId);
    }

    public int deleteByPatient(String patientId){
        return connectionDao.deleteByPatient(patientId);
    }
}
