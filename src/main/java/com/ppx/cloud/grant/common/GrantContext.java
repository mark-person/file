package com.ppx.cloud.grant.common;


/**
 * 分配权限上下文
 * @author mark
 * @date 2018年6月20日
 */
public class GrantContext {
	
	public static ThreadLocal<LoginAccount> threadLocalAccount = new ThreadLocal<LoginAccount>();

	public static void setLoginAccount(LoginAccount mer) {
	    threadLocalAccount.set(mer);
	}
	
	public static LoginAccount getLoginAccount() {
	   return threadLocalAccount.get();
	}
	
}
