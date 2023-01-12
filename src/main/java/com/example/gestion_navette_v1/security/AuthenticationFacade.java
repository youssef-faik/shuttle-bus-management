package com.example.gestion_navette_v1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
  @Override
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  
  @Override
  public CustomUserDetails getCustomUserDetails() {
    if (!getAuthentication().isAuthenticated()) {
      return null;
    }
    
    return (CustomUserDetails)getAuthentication().getPrincipal();
  }
}
