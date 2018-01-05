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
                //������Щ���ö�����APP������ʱ����趨�ˣ��Ժ󲻹�ʲôhttp request�������������ٴ����������
                //��Ӧ�����Զ��׼��ģ���Ȼ��ô��configure�أ�
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

            //����ǹ���hasRole�ķǳ��ؼ���һ����д�ķ������������������spring�ͻ�֪����ǰ����û�����Ϣ��
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                /*
                ע�⣬Ҫ��spring�ܹ����뵽������������ȵ�Ҫ��filter��һЩ�������ǳ����ֶ�
                ���ؼ���һ������Ҫ����security context����setAuthentication(token)
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
