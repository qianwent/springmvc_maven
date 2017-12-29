package com.qwt.springmaven.security.filter;

import com.qwt.springmaven.common.security.UserDetailsAdapter;
import com.qwt.springmaven.common.util.PrincipalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter implementation to initialize the spring security context with 
 * the principal which has been pre-authenticated via clear trust.
 * 
 * 
 * @author Wentao
 *
 */
public class SecurityContextInitFilter implements Filter {
	
	Logger logger = LoggerFactory.getLogger(SecurityContextInitFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		logger.debug("Placing preauthenticated authentication token in spring security context...");
		UserDetailsAdapter user = new UserDetailsAdapter(PrincipalUtil.getPrincipal());
		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(user,user.getUsername(),user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		return;
	}

	public void destroy() {
		return;
		
	}
	
}
