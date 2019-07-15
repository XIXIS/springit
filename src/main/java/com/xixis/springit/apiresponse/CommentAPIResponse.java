package com.xixis.springit.apiresponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.xixis.springit.apiresponse.APIResponse;
import com.xixis.springit.domain.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentAPIResponse extends APIResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Comment> comments=null;

  public CommentAPIResponse(List<Comment> comments, String error, String message) {
    super(error, message);
    this.comments = comments;
  }

  public CommentAPIResponse(List<Comment> comments, String message) {
    super(message);
    this.comments = comments;
  }

  public CommentAPIResponse(String error, String message) {
    super(error, message);
  }

  public CommentAPIResponse(String message) {
    super(message);
  }


}
