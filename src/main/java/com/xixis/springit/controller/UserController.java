package com.xixis.springit.controller;

import com.xixis.springit.domain.User;
import com.xixis.springit.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  private UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // list

  @GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public List<User> list() {
    return userRepository.findAll();
  }

}
