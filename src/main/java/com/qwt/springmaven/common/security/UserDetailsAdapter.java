package com.qwt.springmaven.common.security;

import com.qwt.springmaven.common.model.Principal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Adapter for Principal to work with Spring Security.
 * 
 * @author Wentao
 *
 */
@SuppressWarnings("serial")
public class UserDetailsAdapter implements UserDetails {
	private Principal principal;

	private Collection<GrantedAuthority> authorities;

	/**
	 * Creates an instance of UserDetailsAdapter
	 *
	 * @param p	principal to be adapted
	 */
	public UserDetailsAdapter(Principal p){
		principal = p;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		if(authorities == null) {
			synchronized(Object.class) {
				if(authorities == null) {
					List<String> groups = principal.getGroups();
					authorities = new ArrayList<GrantedAuthority>(groups.size());

					/*for(String g : groups){
						authorities.add(new SimpleGrantedAuthority(g));
					}*/
					for (int x=0; x < groups.size(); x++) {
						authorities.add(new SimpleGrantedAuthority(groups.get(x)));
					}
				}
			}
		}
		return authorities;
	}

	public String getPassword() {
		// We are not using authentication features, so I don't think we really need a password...
		return "";
	}

	public String getUsername() {
		return principal.getUserId();
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		return true;
	}
}
