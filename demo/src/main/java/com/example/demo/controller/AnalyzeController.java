package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Flow;
import com.example.demo.domain.Test;
import com.example.demo.utils.AnalyzeUtil;
import com.example.demo.utils.POIUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class AnalyzeController {
    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public Test analyze(@RequestBody HashMap<String,String> parasmap){
        String params=parasmap.get("flows");
        JSONObject jb = JSONObject.parseObject(params);
        List<Flow> flows = JSONObject.parseArray(jb.getJSONArray("params").toJSONString(), Flow.class);
        String vv=parasmap.get("vv");
        return AnalyzeUtil.analyze(flows,Integer.parseInt(vv));
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<Flow> get(MultipartFile file){
        List<Flow>flows= POIUtil.importFlow(file);
        return flows;
    }
}
