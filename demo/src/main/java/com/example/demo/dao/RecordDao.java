package com.example.demo.dao;

import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordDao {

    /*日志前缀*/
    public List<PreRecord> getPre(@Param("firstDate")String firstDate, @Param("lastDate")String lastDate, @Param("username") String username);

    public PreRecord getPreByDate(@Param("recordDate")String recordDate, @Param("username") String username);

    public int insertPre(PreRecord preRecord);

    public int removePre(Map<String, Object> params);

    /*日志后缀*/
    public List<SufRecord> getSuf(@Param("beginId")Integer beginId,@Param("endId")Integer endId);

    public int getLastIdSuf();

    public int insertTempSuf(@Param("username") String username);

    public int removeSuf(@Param("beginId")Integer beginId,@Param("endId")Integer endId);
}
