package com.xixis.springit.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
}