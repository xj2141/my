package com.example.demo.dao;

import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordDao {

    /*日志前缀*/
    public List<PreRecord> getPre();

    public PreRecord getPreByDate(String recordDate);

    public int insertPre(PreRecord preRecord);

    public int removePre(String []recordDate);

    /*日志后缀*/
    public List<SufRecord> getSuf(@Param("beginId")Integer beginId,@Param("endId")Integer endId);

    public int getLastIdSuf();

    public int insertTempSuf();

    public int insertSuf(List<SufRecord>suf);

    public int removeSuf(String []recordId);
}
