package com.example.demo.dao;

import com.example.demo.domain.Evaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EvaluateDao {

    public Evaluate findByEvaluate(Evaluate evaluate);

    public List<Evaluate> queryPage(@Param("pageSize")Integer pageSize, @Param("startRows")Integer startRows, @Param("evaluateName") String evaluateName);

    public int getRowCount(String evaluateName);

    public int insert(Evaluate evaluate);

    public int delete(int evaluateId);

    public int remove(String []id);

    public int update(Evaluate evaluate);
}
