package org.phoenix.paywall.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.phoenix.paywall.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  private String firstName;
  private String lastName;
  private Boolean active;
  @JsonIgnore
  private String password;

  private String role;

  private Collection<? extends GrantedAuthority> authorities;

  private String associatedPartyIds;

  private String addedPartyIds;

  public UserDetailsImpl(Long id, String username, String password, String firstName, String lastName, String role, Boolean active, String associatedPartyIds, String addedPartyIds) {
    this.id = id;
    this.username = username;
    // this.email = email;
    this.password = password;
    // this.authorities = authorities;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.active = active;
    this.associatedPartyIds = associatedPartyIds;
    this.addedPartyIds = addedPartyIds;
  }

  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(
        user.getUserId(), 
        user.getUsername(), 
        user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole(), user.getActive(), user.getAssociatedPartyIds(), user.getAddedPartyIds());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Boolean getActive(){
    return active;
  }

  public Boolean setActive() {
    active = true;
    return active;
  }
  
  public String getAssociatedPartyIds() {
    return associatedPartyIds;
  }

  public String getAddedPartyIds() {
    return addedPartyIds;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getRole() {
    return role;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
