package com.qwt.springmaven.common.util;


import com.qwt.springmaven.common.model.Principal;

/**
 * Utility class for accessing the user principal.
 * <p>
 * The implementation of this class stores the principal within a thread-local
 * variable allowing for thread-safe access to the princpal for the end-user who
 * initiated the request.
 * 
 * @author Wentao
 *
 */
public class PrincipalUtil {
	/** thread-local used to store princpal. */
	private static ThreadLocal<Principal> principal = new ThreadLocal<Principal>(){
			@Override
			protected Principal initialValue(){
				return null;
			}
		}; 
	
	/**
	 * Returns the principal for the authenticated user.
	 * 
	 * @return	principal for the authenticated user
	 */
	public static Principal getPrincipal(){
		return principal.get();
	}
	
	/**
	 * Sets the principal for the authenticated user.
	 * 
	 * @param p	principal for authenticated user
	 */
	public static void setPrincipal(Principal p){
		principal.set(p);
	}
	
}
