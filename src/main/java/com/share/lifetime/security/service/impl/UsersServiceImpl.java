package com.share.lifetime.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.lifetime.security.domain.entity.Users;
import com.share.lifetime.security.mapper.UsersMapper;
import com.share.lifetime.security.service.UsersService;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public int insert(Users record) {
        return usersMapper.insert(record);
    }

    @Override
    public int insertSelective(Users record) {
        return usersMapper.insertSelective(record);
    }

    @Override
    public Users getUsersWithCondition(Users condition) {
        return usersMapper.getUsersWithCondition(condition);
    }

    @Override
    public List<Users> listUsersWithCondition(Users condition) {
        return usersMapper.listUsersWithCondition(condition);
    }

}
