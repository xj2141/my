package com.example.demo.controller;

import com.example.demo.domain.Evaluate;
import com.example.demo.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public List<Evaluate> page(Integer page,String evaluateName,String click){
        int pageNow = page == null ? 1 : page;
        int pageSize = 2;
        int startRows = pageSize*(pageNow-1);
        if(click=="no")evaluateName="";
        List<Evaluate> list= evaluateService.queryPage(startRows,evaluateName);
        return list;
    }

    @RequestMapping(value = "/rows", method = RequestMethod.POST)
    @ResponseBody
    public int rows(String evaluateName,String click){
        if(click=="no")evaluateName="";
        int re=evaluateService.getRowCount(evaluateName);
        return re;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(Integer evaluateId) {
        int result = evaluateService.delete(evaluateId);
        return result;
    }

    //批量删除
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Integer remove(@RequestBody HashMap<String,String> parasmap) {
        String ids=parasmap.get("ids");
        String []id=ids.split(",");
        int result=evaluateService.remove(id);
        return result;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Integer insert(Evaluate evaluate) {
        if(evaluateService.findByEvaluate(evaluate)==null){
            return evaluateService.insert(evaluate);
        }else{
            return evaluateService.update(evaluate);
        }
    }

}