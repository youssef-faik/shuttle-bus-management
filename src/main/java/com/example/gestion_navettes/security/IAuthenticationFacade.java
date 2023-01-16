package com.example.gestion_navettes.security;

import com.example.gestion_navettes.security.entities.AppUser;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
  Authentication getAuthentication();
  AppUser getCurrentAuthenticatedUser();
}
