package com.xixis.springit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class SpringitApplication {

  // private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringitApplication.class, args);
  }

}
