package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class VoteAPIResponse extends APIResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Vote vote = null;

  public VoteAPIResponse(Vote vote, String error, String message) {
    super(error, message);
    this.vote = vote;
  }

  public VoteAPIResponse(Vote vote, String message) {
    super(message);
    this.vote = vote;
  }

  public VoteAPIResponse(String error, String message) {
    super(error, message);
  }

  public VoteAPIResponse(String message) {
    super(message);
  }

}


