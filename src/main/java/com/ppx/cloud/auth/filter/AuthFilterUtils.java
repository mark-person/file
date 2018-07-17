package com.ppx.cloud.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ppx.cloud.auth.common.AuthUtils;
import com.ppx.cloud.auth.common.LoginAccount;
import com.ppx.cloud.common.util.CookieUtils;

/**
 * 权限过滤工具
 * @author mark
 * @date 2018年7月2日
 */
public class AuthFilterUtils {
 
    
    private static final Logger logger = LoggerFactory.getLogger(AuthFilterUtils.class);
    
    
    /**
     * # 跳到login的情况 
     * 1. 没有token 
     * 2. tockcen校验异常(token不同法或jwt密码被改) 
     * 3. 帐号状态异常
     * 4. modified不同（账号和密码被修改时）
     * # 说明 1.token过期时，将重新验证，合法就产生新的token，不合法就跳到login页
     * 获取token里的用户信息
     * 
     * @param request
     * @return 返回null则跳到login页
     */
    public static LoginAccount getLoginAccout(HttpServletRequest request, HttpServletResponse response, String uri) {

        // 从cookie中取得token
        String token = CookieUtils.getCookieMap(request).get(AuthUtils.PPXTOKEN);

        // token为空,表示未登录
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        
        Algorithm algorithm = null;
        DecodedJWT jwt = null;
        try {
            algorithm = Algorithm.HMAC256(AuthUtils.getJwtPassword());
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // 不打印轨迹(token不合法或jwt密码修改，防止攻击)
            logger.error(e.getMessage());
            return null;
        }

        Integer accountId = jwt.getClaim("accountId").asInt();
        String loginAccount = jwt.getClaim("loginAccount").asString();
        Integer merId = jwt.getClaim("merId").asInt();
        String merName = jwt.getClaim("merName").asString();
        
        LoginAccount account = new LoginAccount();
        account.setAccountId(accountId);
        account.setLoginAccount(loginAccount);
        account.setMerId(merId);
        account.setMerName(merName);
        return account;
    }
    
}
