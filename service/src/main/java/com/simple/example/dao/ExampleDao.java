package com.simple.example.dao;

import com.simple.example.model.Example;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExampleDao {

    @Select("SELECT * FROM account WHERE state = #{state}")
    Example findByState(@Param("state") String state);
}
