package com.xixis.springit.apiresponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.xixis.springit.apiresponse.APIResponse;
import com.xixis.springit.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserAPIResponse extends APIResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private User user=null;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<User> users=null;

  public UserAPIResponse(User user, String error, String message) {
    super(error, message);
    this.user = user;
  }

  public UserAPIResponse(User user, String message) {
    super(message);
    this.user = user;
  }

  public UserAPIResponse(String error, String message) {
    super(error, message);
  }

  public UserAPIResponse(String message) {
    super(message);
  }

  public UserAPIResponse(List<User> users, String message) {
    super(message);
    this.users = users;
  }


}
