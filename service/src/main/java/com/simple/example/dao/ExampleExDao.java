package com.simple.example.dao;

import com.simple.example.model.ExampleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 使用Mybatis SQl Java 注解的方式方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */

@Mapper
public interface ExampleExDao {

    @Select("SELECT * FROM account WHERE state = #{state}")
    ExampleModel findByState(@Param("state") String state);
}
