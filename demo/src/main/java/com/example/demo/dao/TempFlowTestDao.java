package com.example.demo.dao;

import com.example.demo.domain.TempFlow;
import com.example.demo.domain.TempTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TempFlowTestDao {

    public int updateDate(@Param("username") String username,@Param("testDate") String testDate);

    //尿流检测
    public List<TempTest> getTest(String username);

    public int getCountTest(String username);

    public int insertTest(TempTest tempTest);

    public int updateTest(TempTest tempTest);

    public int deleteTest(int testId);

    public int removeTest(String username);

    //尿流率数据
    public List<TempFlow> getFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);

    public int getLastIdFlow();

    public int insertFlow(List<TempFlow>tempFlows);

    public int deleteFlow(@Param("beginId")Integer beginId, @Param("endId")Integer endId);
}
