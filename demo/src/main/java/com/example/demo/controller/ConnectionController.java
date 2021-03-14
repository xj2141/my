package com.example.demo.controller;

import com.example.demo.domain.Connection;
import com.example.demo.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/getByDoctor", method = RequestMethod.POST)
    public List<Connection> getByDoctor(String doctorId) {
        return connectionService.getByDoctor(doctorId);
    }

    @RequestMapping(value = "/findByPatient", method = RequestMethod.POST)
    public List<Connection> findByPatient(String patientId) {
        System.out.println(patientId);
        List<Connection>connections=connectionService.findByPatient(patientId);
        System.out.println(connections.size());
        return connections;
    }

    @RequestMapping(value = "/findByConnection", method = RequestMethod.POST)
    public Map<String, Object> findByConnection(Connection connection) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (connectionService.findByConnection(connection)!=null) {
            map.put("status", "success");
        } else {
            map.put("status", "error");
        }
        return map;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@RequestBody HashMap<String,String> parasmap) {
        String patientId=parasmap.get("patientId");
        connectionService.deleteByPatient(patientId);
        String ids=parasmap.get("ids");
        String []doctorId=ids.split(",");
        Connection connection=new Connection();
        connection.setPatientId(patientId);
        for(int i=0;i<doctorId.length;i++){
            connection.setDoctorId(doctorId[i]);
            connectionService.insert(connection);
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(Connection connection) {
        connectionService.insert(connection);
    }

    @RequestMapping(value = "/deleteByDoctor", method = RequestMethod.POST)
    public void deleteByDoctor(String doctorId) {
        connectionService.deleteByDoctor(doctorId);
    }

    @RequestMapping(value = "/deleteByPatient", method = RequestMethod.POST)
    public void deleteByPatient(String patientId) {
        connectionService.deleteByPatient(patientId);
    }
}
