package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;

  @ManyToMany( mappedBy = "roles")
  @JsonBackReference
  private Collection<User> users;


}
