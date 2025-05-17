package com.ymj.campuscanvas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class CampusCanvasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusCanvasApplication.class, args);
    }

}
