package com.qwt.springmaven.config;

import com.qwt.springmaven.security.filter.PrincipalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Wentao Qian on 12/25/2017.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();

        http.csrf().disable()
                .addFilterBefore(principalFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
                .anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("Calling SecurityConfig.configureGlobal()");
        //TODO: 直接调用userDetailsService，暂时跳过复杂的preauth
        auth.userDetailsService(userDetailsService());
    }

    private PrincipalFilter principalFilter() {
        PrincipalFilter principalFilter = new PrincipalFilter();
        principalFilter.setUserIdHeader("http_user");
        return principalFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
         logger.debug("Calling SecurityConfig.userDetailsService()");
        return new UserDetailsService() {
            Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

            //这个是关于hasRole的非常关键的一个重写的方法，有了这个方法，spring就会知道当前这个用户的信息了
            //username就是通过表单提交的，这个make sense
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Collection<GrantedAuthority> auths = new ArrayList<>();

                SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_ADMIN");
                SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER");

                if(username.equals("will")){
                    auths= new ArrayList<>();
                    auths.add(auth1);
                    auths.add(auth2);
                }

                User user = new User(username, "aaa", true, true, true, true, auths);
                return user;
            }
        };
    }

}
