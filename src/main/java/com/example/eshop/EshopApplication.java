  package com.example.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;

@SpringBootApplication
public class EshopApplication {
  public static void main(String[] args) {
    SpringApplication.run(EshopApplication.class, args);
  }
}
