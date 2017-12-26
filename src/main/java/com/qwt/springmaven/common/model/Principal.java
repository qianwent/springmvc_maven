package com.qwt.springmaven.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Type used to encapsulate data about the user principal.
 * 
 * @author Wentao
 *
 */
public class Principal {

	private String userId;
	private List<String> groups = new ArrayList<String>();
	
	/**
	 * Returns the user id for the user.
	 * <p>
	 * This is not the username, but rather the clear trust id
	 * for the user.
	 * 
	 * @return user id for the user
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id for the user.
	 * 
	 * @param userId	user id for the user
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 *	Returns the roles the user belongs to, if any.
	 *
	 *	@return roles the user belongs to
	 */
	public List<String> getGroups() {
		return groups;
	}

	/**
	 * Sets the groups the user belongs to.
	 * 
	 * 
	 * @param groups	groups the user belongs to
	 */
	public void setGroups(List<String> groups) {
		synchronized(groups) {
			this.groups.clear();
			this.groups.addAll(groups);
		}
	}
	
	/**
	 * Adds a group to the user.
	 * 
	 * @param group	group being added to the user
	 */
	public void addGroup(String group){
		this.groups.add(group);
	}

	/**
	 * Adds a collection of groups to the user.
	 * 
	 * @param groups	groups to be added
	 */
	public void addGroups(List<String> groups){
		this.groups.addAll(groups);
	}

}
