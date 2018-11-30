package com.share.lifetime.security.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.share.lifetime.security.domain.entity.Users;

public interface UsersService {

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    int insert(Users record);

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    int insertSelective(Users record);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Users getUsersWithCondition(@Param("condition") Users condition);

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    List<Users> listUsersWithCondition(@Param("condition") Users condition);
}
