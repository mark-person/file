package com.ppx.cloud.file.index;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileIndexController {
    
	@GetMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // response.sendRedirect(request.getContextPath() + "/login/login");
	    System.out.println("...........index:");
	}
	
	
   
}