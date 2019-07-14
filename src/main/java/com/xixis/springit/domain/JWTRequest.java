package com.xixis.springit.domain;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class JWTRequest implements Serializable {

  private static final long serialVersionUID = 5926468583005150707L;
  private String username;
  private String password;

  //need default constructor for JSON Parsing
  public JWTRequest() { }

  public JWTRequest(String username, String password) {
    this.setUsername(username);
    this.setPassword(password);
  }

}
