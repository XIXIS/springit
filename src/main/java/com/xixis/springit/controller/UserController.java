package com.xixis.springit.controller;

import com.xixis.springit.domain.User;
import com.xixis.springit.apiresponse.UserAPIResponse;
import com.xixis.springit.service.UserService;
import com.xixis.springit.validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // list

  @GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list() {

    List<User> users = userService.listAll();
    return new ResponseEntity<>(
        new UserAPIResponse(users, "Users list"),
        HttpStatus.OK
    );
  }

  @PostMapping(value = "/users/create", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@Valid @RequestBody User user,  BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      UserValidator userValidator = new UserValidator(bindingResult);
      String error = userValidator.validateRegisterUser();
      return new ResponseEntity<>(
          new UserAPIResponse(error, "Validation Failed!"),
          HttpStatus.UNPROCESSABLE_ENTITY
      );
    }

    User newUser = userService.register(user);
    return new ResponseEntity<>(
        new UserAPIResponse(newUser, "User registered successfully"),
        HttpStatus.OK
    );
  }

}
