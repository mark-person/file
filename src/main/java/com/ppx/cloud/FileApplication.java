package com.ppx.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ppx.cloud.common.util.ApplicationUtils;

@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        ApplicationUtils.context = SpringApplication.run(FileApplication.class, args);
    }

}