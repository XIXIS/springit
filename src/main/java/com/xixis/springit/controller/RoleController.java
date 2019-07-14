package com.xixis.springit.controller;

import com.xixis.springit.domain.RoleListAPIResponse;
import com.xixis.springit.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
  private RoleRepository roleRepository;

  public RoleController(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  // list

  @GetMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> list() {
    return new ResponseEntity<>(
        new RoleListAPIResponse(roleRepository.findAll(), "All roles listed"),
        HttpStatus.OK
    );
  }

}
