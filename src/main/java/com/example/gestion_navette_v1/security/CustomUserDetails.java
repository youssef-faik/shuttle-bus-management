package com.example.gestion_navette_v1.security;

import com.example.gestion_navette_v1.security.entities.AppUser;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
  private AppUser appUser;
  
  public CustomUserDetails(AppUser appUser) {
    this.appUser = appUser;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    appUser.getRoles().forEach(
            role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()))
    );
    
    return grantedAuthorities;
  }
  
  @Override
  public String getPassword() { return appUser.getPassword(); }
  
  @Override
  public String getUsername() {
    return appUser.getEmail();
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
  
  //  Indicates whether the user is enabled or disabled.
  //  A disabled user cannot be authenticated.
  @Override
  public boolean isEnabled() {
    return appUser.isActive();
  }
}
