package com.cicl.frame.security.sysuser.service;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cicl.frame.security.authority.entity.Authority;
import com.cicl.frame.security.role.entity.Role;
import com.cicl.frame.security.sysuser.entity.SysUser;
import com.cicl.frame.security.sysuser.util.SpringSecurityUtils;

/**
 * Class SysUserDetailsService
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-22 下午02:24:48
 */
public class SysUserDetailsService implements UserDetailsService {
	private static final Log LOG = LogFactory.getLog(SysUserDetailsService.class);
	@Autowired
	SysUserService sysUserService;

	/* load user by username for spring-security.
	 * SysUser implements org.springframework.security.core.userdetails.UserDetails interface 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		SysUser sysUser = sysUserService.loadByLoginName(username);	
		if(null == sysUser) throw new UsernameNotFoundException("User with name ["+ username +"] not found.");
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		if(sysUser!=null){			
			for(Role role : sysUser.getRoles()) {
				for(Authority authority : role.getAuthorities()){
					authorities.add(authority);
				}
			}
		}
		
		sysUser.setAuthorities(authorities);
		return sysUser;
	}
	

	/**
	 * 取得当前用户, 返回值为系统扩展的SysUser, 如果当前用户未登录则返回null.
	 */
	public static SysUser getCurrentSysUser() {
		Authentication authentication = SpringSecurityUtils.getAuthentication();

		if (authentication == null) {
			return null;
		}

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof SysUser)) {
			return null;
		}

		return (SysUser) principal;
	}
	
	public static Long getCurrentSysUserId() {
		SysUser sysUser = getCurrentSysUser();
		if( sysUser==null ){
			return null;
		}
		else{
			return sysUser.getId();
		}
	}
	
	public static boolean isCurrentUser(Long id){
		if(id==null) return false;
		Long curId = getCurrentSysUserId();
		
		return curId.equals(id);
	}


}
