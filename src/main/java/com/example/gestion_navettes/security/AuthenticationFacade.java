package com.example.gestion_navettes.security;

import com.example.gestion_navettes.security.entities.AppUser;
import com.example.gestion_navettes.security.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements IAuthenticationFacade {
  final AppUserService appUserService;
  
  @Override
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
  
  @Override
  public AppUser getCurrentAuthenticatedUser() {
    if (!getAuthentication().isAuthenticated()) {
      return null;
    }
  
    CustomUserDetails userDetails = (CustomUserDetails) getAuthentication().getPrincipal();
    
    return appUserService.getUserByEmail(userDetails.getUsername());
  }
}
