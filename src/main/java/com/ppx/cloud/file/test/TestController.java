package com.ppx.cloud.file.test;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ppx.cloud.auth.common.AuthContext;
import com.ppx.cloud.auth.common.AuthUtils;
import com.ppx.cloud.auth.common.LoginAccount;
import com.ppx.cloud.common.controller.ControllerReturn;
import com.ppx.cloud.common.page.Page;
import com.ppx.cloud.common.util.CookieUtils;
import com.ppx.cloud.common.util.DateUtils;


@Controller
public class TestController {
    
    @Autowired
    private TestServiceImpl impl;
    
    @GetMapping @ResponseBody
    public Map<String, Object> login(HttpServletResponse response) throws Exception {
        
        Algorithm algorithm = Algorithm.HMAC256(AuthUtils.getJwtPassword());
        String token = JWT.create().withIssuedAt(new Date())
                .withClaim("accountId", -1)
                .withClaim("loginAccount", "admin")
                .withClaim("merId", -1)
                .withClaim("merName", "admin")
                .sign(algorithm);
        CookieUtils.setCookie(response, AuthUtils.PPXTOKEN, token);
        
        return ControllerReturn.ok();
    }
    
    @GetMapping
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        
        
        
        String token = CookieUtils.getCookieMap(request).get(AuthUtils.PPXTOKEN);
        
        mv.addObject("tokenName", AuthUtils.PPXTOKEN);
        mv.addObject("token", token);
        
        
        return mv;
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> post() {
        LoginAccount a = AuthContext.getLoginAccount();
        
        System.out.println("-------post:" + a.getMerName());
        return ControllerReturn.ok();
    }
    
    @GetMapping
    public ModelAndView upload() {
        ModelAndView mv = new ModelAndView();
        return mv;
    }
    
    @GetMapping
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", list(new Page(), new Test()));
        mv.addObject("today", DateUtils.today());
        return mv;
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> list(Page page, Test bean) {
        return ControllerReturn.ok(impl.list(page, bean), page);
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> insert(Test bean) {
        return ControllerReturn.ok(impl.insert(bean));
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> get(@RequestParam Integer id) {
        return ControllerReturn.ok(impl.get(id));
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> update(Test bean) {
        return ControllerReturn.ok(impl.update(bean));
    }
    
    @PostMapping @ResponseBody
    public Map<String, Object> delete(@RequestParam Integer id) {
        return ControllerReturn.ok(impl.delete(id));
    }
	
    
}