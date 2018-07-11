package com.ppx.cloud.file.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ppx.cloud.common.controller.ControllerReturn;
import com.ppx.cloud.common.page.Page;
import com.ppx.cloud.common.util.DateUtils;


@Controller
public class TestController {
    
    @Autowired
    private TestServiceImpl impl;
    
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