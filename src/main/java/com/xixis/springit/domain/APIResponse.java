package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xixis.springit.domain.Link;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class APIResponse {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String error="";
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String message;


  public APIResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public APIResponse(String message) {
    this.message = message;
  }



}
