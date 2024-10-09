package fr.groupbees.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"fr.groupbees.injection"})
public class WorldCupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldCupApplication.class, args);
    }
}
