package com.example.demo.dao;

import com.example.demo.domain.SecTempRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecTempRecordDao {

    public List<SecTempRecord> get();

    public int getCount();

    public int insert(SecTempRecord secTempRecord);

    public int update(SecTempRecord secTempRecord);

    public int delete(int recordId);

    public int remove();
}
