package com.share.lifetime.security.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author LIAOXIANG
 * @date 2018/11/11
 */
@Slf4j
public class SpringDataUserDetailsService extends JdbcDaoImpl {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:[{}]", username);
        return super.loadUserByUsername(username);
    }

}
