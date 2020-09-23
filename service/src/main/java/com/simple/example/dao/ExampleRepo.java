package com.simple.example.dao;

import com.simple.example.model.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * 使用Hibernate的JPA方式进行数据访问的样例
 * @author zhangyq
 * @version v1.0 ExampleRepo.java
 */


@Repository
public interface ExampleRepo extends JpaRepository<Example, String> {

    Example findAccountById(String id);

    Example findAccountByEmail(String email);
    Example findAccountByName(String name);

    Example findAccountByPhoneNumber(String phoneNumber);

    @Modifying(clearAutomatically = true)
    @Query("update Example account set account.email = :email, account.confirmedAndActive = true where account.id = :id")
    @Transactional
    int updateEmailAndActivateById(@Param("email") String email, @Param("id") String id);

}
