package com.ppx.cloud.auth.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.cloud.auth.filter.AuthFilterUtils;
import com.ppx.cloud.common.exception.custom.PermissionUrlException;




/**
 * 权限拦截器
 * @author mark
 * @date 2018年6月19日
 */
public class AuthInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI().replace(contextPath, "");
		
		// 不拦截
		if (uri.startsWith("/test/login")) {
			return true;
		}
		
		LoginAccount account = AuthFilterUtils.getLoginAccout(request, response, uri);
		if (account == null) {
		    throw new PermissionUrlException("Unauthorized.forbidden:" + uri);
		} else {
			AuthContext.setLoginAccount(account);
		}

		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}