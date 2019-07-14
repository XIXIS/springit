package com.xixis.springit.domain;

import com.xixis.springit.config.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vote extends Auditable {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private short direction;

  // user
//  @NonNull
//  @OneToOne
//  private User user;

  // link
  @NonNull
  @ManyToOne
  private Link link;

}
