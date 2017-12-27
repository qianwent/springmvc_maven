package com.qwt.springmaven.config;

import com.qwt.springmaven.common.security.UserDetailsAdapter;
import com.qwt.springmaven.common.util.PrincipalUtil;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by Wentao Qian on 12/25/2017.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

//    @Autowired
//    @Qualifier("dataSource")
//    DataSource dataSource;

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username,password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery(
//                        "select username, role from user_roles where username = ?");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
                ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("Calling SecurityConfig.configureGlobal()");
        auth.authenticationProvider(preauthAuthProvider());
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
        logger.debug("Calling SecurityConfig.preauthAuthProvider()");
        PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
        preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
        return preauthAuthProvider;
    }

    @Bean
    public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
        logger.debug("Calling SecurityConfig.userDetailsServiceWrapper()");
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>();
        wrapper.setUserDetailsService(userDetailsService());
        return wrapper;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.debug("Calling SecurityConfig.userDetailsService()");
        return new UserDetailsService() {
            Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

            //!!!这个目前看来是关于hasRole的非常关键的一个重写的方法，有了这个方法，spring就会知道当前这个用户的信息了
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDetailsAdapter ud = new UserDetailsAdapter(PrincipalUtil.getPrincipal());
                logger.debug("UserDetailsService.loadUserByUsername returning " + ud.getUsername());
                return ud;
            }
        };
    }

}
