/** 
 * @(#)SysUser.java 1.0.0 2011-4-22 下午04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.sysuser.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cicl.frame.core.entity.AbstractEntity;
import com.cicl.frame.security.organization.entity.Organization;
import com.cicl.frame.security.role.entity.Role;
import com.cicl.frame.security.sysuser.dictionary.SysUserConstant;

/**
 * Class SysUser
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */
public class SysUser extends AbstractEntity<Long> implements UserDetails {
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Length(max = 200)
	private String username;
	@NotEmpty
	@Length(max = 200)
	private String loginName;
	@Length(max = 200)
	private String password;
	@Length(max = 200) @Email
	private String email;

	private Long status;

	private boolean accountNonExpired;

	private boolean credentialsNonExpired;

	private boolean enabled;

	private boolean accountNonLocked;

	Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	
	private Set<Role> roles;

	private Long organizationId;
	
	private Organization organization;
	
	@NotEmpty
	@Length(max = 200)
	private String code;
	@NotEmpty @Length(max = 200)
	private String mobile;
	@Length(max = 200)
	private String phone;
	@Length(max = 200)
	private String address;
	@Length(max = 2000)
	private String description;
	@Length(max = 2000)
	private String recentPassword;

	private boolean isLogin;

	private Long tryTimes;

	private Long failLoginTimes;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	//@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//@JsonIgnore
	public String getRecentPassword() {
		return this.recentPassword;
	}

	public void setRecentPassword(String recentPassword) {
		this.recentPassword = recentPassword;
	}

	//@JsonIgnore
	public boolean isIsLogin() {
		return this.isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	//@JsonIgnore
	public Long getTryTimes() {
		return this.tryTimes;
	}

	public void setTryTimes(Long tryTimes) {
		this.tryTimes = tryTimes;
	}

	//@JsonIgnore
	public Long getFailLoginTimes() {
		return this.failLoginTimes;
	}

	public void setFailLoginTimes(Long failLoginTimes) {
		this.failLoginTimes = failLoginTimes;
	}
	// @Override
	@JsonIgnore
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	@JsonIgnore
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@JsonIgnore
	public Organization getOrganization() {
		return organization;
	}	
	public String getOrganizationName() {
		if(getOrganization()==null)return "";
		return getOrganization().getName();
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return SysUserConstant.ENTITY_TYPE;
	}
}
