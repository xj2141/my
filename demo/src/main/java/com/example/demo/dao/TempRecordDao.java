package com.example.demo.dao;

import com.example.demo.domain.TempPreRecord;
import com.example.demo.domain.TempSufRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TempRecordDao {

    /*临时日志前缀*/
    public List<TempPreRecord> getPre(String username);

    public int insertPre(TempPreRecord tempPreRecord);

    public int updatePre(TempPreRecord tempPreRecord);

    public int removePre(String username);

    /*临时日志后缀*/
    public List<TempSufRecord> getSuf(String username);

    public int getCountSuf(String username);

    public int insertSuf(TempSufRecord tempSufRecord);

    public int updateSuf(TempSufRecord tempSufRecord);

    public int deleteSuf(int recordId);

    public int removeSuf(String username);
}
