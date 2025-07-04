package com.springjpa.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Active la configuration automatique, le scan des composants, etc.
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args); // Lance l'application
    }
}
