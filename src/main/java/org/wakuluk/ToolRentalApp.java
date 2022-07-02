package org.wakuluk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ToolRentalApp {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(ToolRentalApp.class, args);
  }
}
