package com.share.lifetime.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.share.lifetime.security.domain.entity.Users;

public interface UsersMapper {
    int insert(Users record);

    int insertSelective(Users record);

    Users getUsersWithCondition(@Param("condition") Users condition);

    List<Users> listUsersWithCondition(@Param("condition") Users condition);

}