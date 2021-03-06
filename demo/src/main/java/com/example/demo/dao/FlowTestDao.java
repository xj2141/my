package com.example.demo.dao;

import com.example.demo.domain.Flow;
import com.example.demo.domain.TestPre;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FlowTestDao {

    //尿流检测
    public List<TestPre> getTestPre(@Param("firstDate")String firstDate, @Param("lastDate")String lastDate);

    public TestPre getTestPreByDate(String testDate);

    public int insertTestPre(TestPre testPre);


    //尿流率数据
    public List<Flow> getFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int insertFlow(List<Flow>flows);

    public int deleteFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int removeFlow();
}
