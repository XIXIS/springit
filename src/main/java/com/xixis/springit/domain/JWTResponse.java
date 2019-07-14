package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class JWTResponse implements Serializable {

  private static final long serialVersionUID = -8091879091924046844L;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String token="";
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String message;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String error;

  public JWTResponse(String token, String message, String error) {
    this.token = token;
    this.message = message;
    this.error=error;
  }

  public JWTResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

}