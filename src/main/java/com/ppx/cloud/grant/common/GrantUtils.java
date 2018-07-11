package com.ppx.cloud.grant.common;

/**
 * 权限工具类
 * @author mark
 * @date 2018年7月2日
 */
public class GrantUtils {
	
	public static final String PPXTOKEN = "PPXTOKEN";
	
	public static String getJwtPassword() {
		return System.getProperty("jwt.password") + "PASS";
	}
}
