package com.share.lifetime.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.share.lifetime.security.handler.CustomAuthenticationSuccessHandler;

/**
 * 
 * @author liaoxiang
 * @date 2018/11/11
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin")
    // .password("password").roles("USER", "ADMIN");
    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().authenticated().and().formLogin()
            .loginPage("/login").defaultSuccessUrl("/", false).permitAll().and().rememberMe().and().logout()
            .permitAll();
    }

    // @Bean
    // @Override
    // protected UserDetailsService userDetailsService() {
    // InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    // manager.createUser(User.withUsername("user").password("password").roles("USER").build());
    // manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
    // manager.createUser(User.withUsername("DBA").password("password").roles("USER", "ADMIN", "DBA").build());
    // return manager;
    // }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return springDataUserDetailsService();
    }

    public SpringDataUserDetailsService springDataUserDetailsService() {
        SpringDataUserDetailsService springDataUserDetailsService = new SpringDataUserDetailsService();
        springDataUserDetailsService.setJdbcTemplate(jdbcTemplate);
        return springDataUserDetailsService;
    }

    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
