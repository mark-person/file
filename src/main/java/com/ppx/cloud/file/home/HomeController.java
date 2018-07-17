package com.ppx.cloud.file.home;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (PrintWriter pw = response.getWriter()) {
            response.setStatus(403);
            pw.write("forbidden");
            pw.flush();
        } 
    }

    @GetMapping
    public ModelAndView home() throws IOException {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

}