package com.example.demo.dao;

import com.example.demo.domain.Connection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConnectionDao {

    public List<Connection> getByDoctor(String doctorId);

    public List<Connection> findByPatient(String patientId);

    public Connection findByConnection(Connection connection);

    public int insert(Connection connection);

    public int update(Connection connection);

    public int deleteByDoctor(String doctorId);

    public int deleteByPatient(String patientId);
}
