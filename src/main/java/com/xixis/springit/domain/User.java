package com.xixis.springit.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
  @NotEmpty(message = "Email is required.")
  private String email;

  @NonNull
  @Column(length = 150)
  @NotEmpty(message = "Password is required.")
  private String password;

  @NonNull
  @Column(nullable = false)
  private boolean enabled;

  @NonNull
  @NotEmpty(message = "First Name is required.")
  private String firstName;

  @NonNull
  @NotEmpty(message = "Last Name is required.")
  private String lastName;

  @Transient
  @Setter(AccessLevel.NONE)
  private String fullName;

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

  public String getFullName(){
    return firstName + " " + lastName;
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
