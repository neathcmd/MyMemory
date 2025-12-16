package com.project.mymemory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.project.mymemory", "com.project.seed"})
public class MyMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMemoryApplication.class, args);
    }
}
