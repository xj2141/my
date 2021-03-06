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
    public List<Evaluate> page(Integer page,Integer pageSize,String evaluateName,String username){
        int pageNow = page == null ? 1 : page;
        int startRows = pageSize*(pageNow-1);
        List<Evaluate> list= evaluateService.queryPage(pageSize,startRows,evaluateName,username);
        return list;
    }

    @RequestMapping(value = "/rows", method = RequestMethod.POST)
    @ResponseBody
    public int rows(String evaluateName,String username){
        int re=evaluateService.getRowCount(evaluateName,username);
        return re;
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
