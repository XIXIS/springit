package com.xixis.springit.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RoleListAPIResponse extends APIResponse{

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Role> roles=null;

  public RoleListAPIResponse(List<Role> roles, String error, String message) {
    super(error, message);
    this.roles = roles;
  }

  public RoleListAPIResponse(List<Role> roles, String message) {
    super(message);
    this.roles = roles;
  }

  public RoleListAPIResponse(String error, String message) {
    super(error, message);
  }

  public RoleListAPIResponse(String message) {
    super(message);
  }


}
