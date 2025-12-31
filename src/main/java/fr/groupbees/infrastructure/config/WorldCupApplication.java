package fr.groupbees.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"fr.groupbees.infrastructure.config"})
public class WorldCupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldCupApplication.class, args);
    }
}
