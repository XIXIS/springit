package com.xixis.springit.domain;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Link extends Auditable{

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  @NotEmpty(message = "Please enter a title.")
  private String title;

  @NonNull
  @NotEmpty(message = "Please enter a url.")
  @URL
  private String url;

  // comment
  @OneToMany(mappedBy = "link")
  private List<Comment> comments = new ArrayList<>();

  public void addComment(Comment comment){
    comments.add(comment);
  }

}
