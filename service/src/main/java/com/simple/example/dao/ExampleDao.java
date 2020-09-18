package com.simple.example.dao;

import com.simple.example.model.Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ExampleDao {

    @Select("SELECT * FROM account WHERE state = #{state}")
    Example findByState(@Param("state") String state);
}
