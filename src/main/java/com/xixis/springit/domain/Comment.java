package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.xixis.springit.config.Auditable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment extends Auditable {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String body;

  //Link
  @ManyToOne
  @NonNull
  @JsonBackReference
  private Link link;

}
