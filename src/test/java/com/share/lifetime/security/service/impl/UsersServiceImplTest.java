package com.share.lifetime.security.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.share.lifetime.security.config.DataConfig;
import com.share.lifetime.security.config.MyBatisConfig;
import com.share.lifetime.security.config.RootConfig;
import com.share.lifetime.security.domain.entity.Users;
import com.share.lifetime.security.service.UsersService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {RootConfig.class,DataConfig.class, MyBatisConfig.class})
public class UsersServiceImplTest {

    @Autowired
    private UsersService usersService;

    @Test
    public void testInsert() {
        Users record = new Users();
        record.setUsername("boss");
        record.setPassword("password");
        record.setEnabled(Boolean.FALSE);
        record.setNickname("管理员");
        record.setRealname("LIAOXIANG");
        record.setRemark("超级管理员");
        usersService.insert(record);
    }

    @Test
    public void testInsertSelective() {
        Users record = new Users();
        record.setUsername("admin");
        record.setPassword("password");
        usersService.insertSelective(record);
    }

}
