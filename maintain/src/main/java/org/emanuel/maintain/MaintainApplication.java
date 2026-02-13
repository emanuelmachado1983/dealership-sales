package org.emanuel.maintain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MaintainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaintainApplication.class, args);
    }

}
