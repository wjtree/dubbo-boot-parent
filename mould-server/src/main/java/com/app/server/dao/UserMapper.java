package com.app.server.dao;

import com.app.api.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByNameAndPwd(@Param("name") String account, @Param("password") String password);

    User selectByName(@Param("name") String account);
}