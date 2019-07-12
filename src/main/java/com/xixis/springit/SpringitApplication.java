package com.xixis.springit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

//  private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringitApplication.class, args);
  }

}
