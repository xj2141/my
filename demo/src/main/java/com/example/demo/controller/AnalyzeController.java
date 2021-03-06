package com.example.demo.controller;

import com.example.demo.domain.Flow;
import com.example.demo.domain.Test;
import com.example.demo.utils.AnalyzeUtil;
import com.example.demo.utils.POIUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class AnalyzeController {
    @RequestMapping(value = "/analyze", method = RequestMethod.POST)
    public Map<String, List> analyze(MultipartFile file){
        Map<String, List> map = new HashMap<String, List>();
        List<Flow>flow= POIUtil.importFlow(file);
        map.put("flow",flow);
        List<Test>test=new ArrayList<>();
        test.add(AnalyzeUtil.analyze());
        map.put("test",test);
        return map;
    }
}
