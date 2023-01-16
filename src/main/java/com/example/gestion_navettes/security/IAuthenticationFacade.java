package com.example.gestion_navettes.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
  Authentication getAuthentication();
  CustomUserDetails getCustomUserDetails();
}
