package com.example.demo.controller;

import com.example.demo.domain.Flow;
import com.example.demo.service.FlowTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flowTest")
public class FlowTestController {

    @Autowired
    private FlowTestService flowTestService;

    @RequestMapping(value = "/getFlow", method = RequestMethod.POST)
    public List<Flow> getFlow(Integer beginId, Integer endId){
        return flowTestService.getFlow(beginId,endId);
    }

    @RequestMapping(value = "/insertFlow", method = RequestMethod.POST)
    public int insertFlow(List<Flow>flows){
        int result= flowTestService.insertFlow(flows);
        return result;
    }

    @RequestMapping(value = "/deleteFlow", method = RequestMethod.POST)
    public int deleteFlow(Integer beginId, Integer endId){
        int result= flowTestService.deleteFlow(beginId, endId);
        return result;
    }

    @RequestMapping(value = "/removeFlow", method = RequestMethod.POST)
    public int removeFlow(){
        int result=flowTestService.removeFlow();
        return result;
    }
}
