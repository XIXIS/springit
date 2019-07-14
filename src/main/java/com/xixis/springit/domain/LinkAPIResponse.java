package com.xixis.springit.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LinkAPIResponse extends APIResponse{

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Link link=null;

  public LinkAPIResponse(Link link, String error, String message) {
    super(error, message);
    this.link = link;
  }

  public LinkAPIResponse(Link link, String message) {
    super(message);
    this.link = link;
  }

  public LinkAPIResponse(String error, String message) {
    super(error, message);
  }

  public LinkAPIResponse(String message) {
    super(message);
  }


}
