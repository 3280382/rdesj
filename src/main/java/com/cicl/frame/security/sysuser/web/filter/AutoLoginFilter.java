package com.cicl.frame.security.sysuser.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import com.cicl.frame.security.sysuser.util.SpringSecurityUtils;

/**
 * Class AutoLoginFilter
 * 自动以默认用户名登录的filter, 用于开发时不需要每次进入登录页面.
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-22 下午02:18:48  
 */
public class AutoLoginFilter extends GenericFilterBean {
	@Autowired
	private UserDetailsService userDetailsService;

	private boolean enabled = false;

	private String defaultUserName;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		//如果被激活且当前用户未登录则进行登录
		if (enabled && SpringSecurityUtils.getCurrentUser() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(defaultUserName);

			if (userDetails == null) {
				throw new RuntimeException("默认用户" + defaultUserName + "不存在");
			}

			SpringSecurityUtils.saveUserDetailsToContext(userDetails, (HttpServletRequest) request);
		}

		chain.doFilter(request, response);
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
}
