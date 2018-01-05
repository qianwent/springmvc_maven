package com.qwt.springmaven.config;

import com.qwt.springmaven.common.security.UserDetailsAdapter;
import com.qwt.springmaven.common.util.PrincipalUtil;
import com.qwt.springmaven.security.filter.PrincipalFilter;
import com.qwt.springmaven.security.filter.SecurityContextInitFilter;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by Wentao Qian on 12/25/2017.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                // just to make sure the filters are in order, but before which one, looks like in low priority
                //.addFilterBefore(principalFilter(), UsernamePasswordAuthenticationFilter.class)
                //以下这些配置都是在APP启动的时候就设定了，以后不管什么http request进来，都不会再触发这个方法
                //这应该是显而易见的，不然怎么叫configure呢？
                .addFilterBefore(principalFilter(), LogoutFilter.class)
                .addFilterAfter(securityContextInitFilter(), PrincipalFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/**").hasRole("USER")
                .anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("Calling SecurityConfig.configureGlobal()");
        auth.authenticationProvider(preauthAuthProvider());
    }

    private PrincipalFilter principalFilter() {
        PrincipalFilter principalFilter = new PrincipalFilter();
        principalFilter.setUserIdHeader("http_user");
        return principalFilter;
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
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
        wrapper.setUserDetailsService(userDetailsService());
        return wrapper;
    }

    @Bean
    public UserDetailsService userDetailsService() {
         logger.debug("Calling SecurityConfig.userDetailsService()");
        return new UserDetailsService() {
            Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

            //这个是关于hasRole的非常关键的一个重写的方法，有了这个方法，spring就会知道当前这个用户的信息了
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                /*
                注意，要让spring能够进入到这个方法，首先得要用filter做一些处理，这是常规手段
                而关键的一步就是要先在security context里面setAuthentication(token)
                 SecurityContextHolder.getContext().setAuthentication(token);
                */
                UserDetailsAdapter ud = new UserDetailsAdapter(PrincipalUtil.getPrincipal());
                logger.debug("UserDetailsService.loadUserByUsername returning " + ud.getUsername());
                System.out.println("UserDetailsService.loadUserByUsername returning " + ud.getUsername());
                return ud;
            }
        };
    }

    @Bean
    SecurityContextInitFilter securityContextInitFilter() {
        SecurityContextInitFilter securityContextInitFilter = new SecurityContextInitFilter();
        return securityContextInitFilter;
    }

}
