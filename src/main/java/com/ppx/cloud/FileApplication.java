package com.ppx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import com.ppx.cloud.common.util.ApplicationUtils;

@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        ApplicationUtils.context = SpringApplication.run(FileApplication.class, args);
        ApplicationHome home = new ApplicationHome(FileApplication.class);
        ApplicationUtils.JAR_HOME = home.getSource().getParent() + "/";
        
        ApplicationUtils.JAR_HOME = "F:\\upload/";
        System.out.println("999999999999999-x");

    }

}