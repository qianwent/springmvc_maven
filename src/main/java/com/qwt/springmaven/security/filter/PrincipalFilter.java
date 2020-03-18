package com.qwt.springmaven.security.filter;

import com.qwt.springmaven.common.model.Principal;
import com.qwt.springmaven.common.util.PrincipalUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter implementation that will extract the login and roles from the user
 * to create an instance of Principal, which is then exposed to the rest of the 
 * application via PrincipalUtil.
 * 
 * @author Wentao
 *
 */
public class PrincipalFilter implements Filter {//TODO: such filter could be included in fw
	
	Logger logger = LoggerFactory.getLogger(PrincipalFilter.class);

	public static final String USER_ID_HEADER_PARAM = "userIdHeader";

	public static final String USER_ID_HEADER_DEFAULT = "http_user";

	private String userIdHeader;
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		return;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//HttpServletResponse res = (HttpServletResponse)response;
		HttpServletRequest req = (HttpServletRequest)request;

		Principal p = createPrincipal(req);

		PrincipalUtil.setPrincipal(p);
		
		// delegate to next filter
		chain.doFilter(request, response);
	}
	
	protected Principal createPrincipal(HttpServletRequest req){
		Principal p = null;

		p = new Principal();
		// populate principal
		//TODO: need to switch to different auth, right now is basic auth
		p.setUserId(getUserId(req));

		// TODO: Get roles from DB logic is not ready yet
		List<String> groups = getGroups();
		p.setGroups(groups);

		return p;
	}
	
	/**
	 * Helper method to extract the login id header value.
	 * 
	 * @param req	http servlet request
	 * @return		value from the login id header or null
	 */
	private String getUserId(HttpServletRequest req){
		return req.getHeader(userIdHeader);
	}

	protected List<String> getGroups() {
		logger.debug("Finding roles from role provider...");
		//TODO: need to get roles from DB
		List<String> groups = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
		logger.debug("Found " + groups.size() + " roles, " + groups);
		System.out.println("Found " + groups.size() + " roles, " + groups);
		return groups;
	}

	protected List<String> getGroups(Principal p) {
		logger.debug("Finding roles from role provider...");
		//TODO: need to get roles from DB
		List<String> groups = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
		logger.debug("Found " + groups.size() + " roles, " + groups);
		return groups;
	}

	public void setUserIdHeader(String loginIdHeader){
		this.userIdHeader = loginIdHeader;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		userIdHeader = StringUtils.defaultIfEmpty(filterConfig.getInitParameter(USER_ID_HEADER_PARAM), USER_ID_HEADER_DEFAULT);
	}

}
