package com.example.logs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class LogsApplication {

//    @Autowired
//    private EmailService service;

    public static void main(String[] args) {
        SpringApplication.run(LogsApplication.class, args);
        }
//    @EventListener(ApplicationReadyEvent.class)
//    public void triggerMail(){
//
//        service.send("umeigboobumneme@gmail.com", "i hate this","this is my mail");
//    }

}
