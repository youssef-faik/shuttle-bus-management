package com.example.gestion_navette_v1.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
  Authentication getAuthentication();
  CustomUserDetails getCustomUserDetails();
}
