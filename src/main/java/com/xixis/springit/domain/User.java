package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @NonNull
  @Column(length = 150)
  private String password;

  @NonNull
  @Column(nullable = false)
  private boolean enabled;

  @ManyToMany( fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id")
  )
  @JsonManagedReference
  private Set<Role> roles = new HashSet<>();

  public void addRole(Role role){
    roles.add(role);
  }

  public void addRoles(Set<Role> roles){
    roles.forEach(this::addRole);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    // List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    // for (Role role: roles){
    //   authorities.add(new SimpleGrantedAuthority(role.getName()));
    // }

    // return authorities;

    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}
