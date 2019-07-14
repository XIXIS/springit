package com.xixis.springit.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentListAPIResponse extends APIResponse{

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Comment> comments=null;

  public CommentListAPIResponse(List<Comment> comments, String error, String message) {
    super(error, message);
    this.comments = comments;
  }

  public CommentListAPIResponse(List<Comment> comments, String message) {
    super(message);
    this.comments = comments;
  }

  public CommentListAPIResponse(String error, String message) {
    super(error, message);
  }

  public CommentListAPIResponse(String message) {
    super(message);
  }


}
