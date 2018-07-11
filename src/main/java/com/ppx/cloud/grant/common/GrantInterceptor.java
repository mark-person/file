package com.ppx.cloud.grant.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.cloud.common.exception.custom.PermissionUrlException;
import com.ppx.cloud.grant.filter.GrantFilterUtils;




/**
 * 权限拦截器
 * @author mark
 * @date 2018年6月19日
 */
public class GrantInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI().replace(contextPath, "");
		
		// 不拦截
		if (uri.startsWith("/test/jwt")) {
			return true;
		}
		
		LoginAccount account = GrantFilterUtils.getLoginAccout(request, response, uri);
		if (account == null) {
		    throw new PermissionUrlException("Unauthorized.forbiddens:" + uri);
		} else {
			GrantContext.setLoginAccount(account);
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