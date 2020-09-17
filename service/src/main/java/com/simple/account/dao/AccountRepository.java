package com.simple.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.simple.account.model.Account;


@Mapper
public interface AccountRepository {

    @Select("SELECT * FROM account WHERE state = #{state}")
    Account findByState(@Param("state") String state);
}
