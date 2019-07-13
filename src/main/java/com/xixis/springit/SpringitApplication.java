package com.xixis.springit;

import com.xixis.springit.domain.Comment;
import com.xixis.springit.domain.Link;
import com.xixis.springit.repository.CommentRepository;
import com.xixis.springit.repository.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

  // private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SpringitApplication.class, args);
  }

  // @Bean
  CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){

    return args -> {
      Link link = new Link("Getting Started with Spring Boot 2","https://therealdanvega.com/spring-boot-2");
      linkRepository.save(link);

      Comment comment = new Comment("This Spring Boot 2 link is awesome!", link);
      commentRepository.save(comment);

      link.addComment(comment);

      System.out.println("We just inserted a link and a comment");
      System.out.println("====================================================");


      // Link firstLink = linkRepository.findByTitle("Getting Started with Spring Boot 2");
      // System.out.println("Getting Inserted Link ==> ");
      // System.out.println(firstLink.getTitle());
      // System.out.println("====================================================");

    };

  }

}
