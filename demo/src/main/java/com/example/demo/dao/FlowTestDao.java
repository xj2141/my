package com.example.demo.dao;

import com.example.demo.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FlowTestDao {

    //尿流检测前缀
    public List<TestPre> getTestPre(@Param("firstDate")String firstDate, @Param("lastDate")String lastDate, @Param("username") String username);

    public TestPre getTestPreByDate(@Param("testDate")String testDate, @Param("username") String username);

    public int insertTestPre(TestPre testPre);

    public int removeTestPre(Map<String, Object> params);

    //尿流检测
    public List<Test> getTest(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int getLastIdTest();

    public int insertTest(List<TempTest>tests);

    public int removeTest(@Param("beginId")Integer beginId,@Param("endId")Integer endId);

    //尿流率数据
    public List<Flow> getFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int insertFlow(List<TempFlow>flows);

    public int removeFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int getLastIdFlow();
}
