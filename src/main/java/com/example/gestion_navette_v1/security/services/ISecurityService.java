package com.example.gestion_navette_v1.security.services;

import com.example.gestion_navette_v1.security.entities.AppRole;
import com.example.gestion_navette_v1.security.entities.AppUser;

public interface ISecurityService {
  AppUser saveAppUser(AppUser appUser);
  AppUser getUserByEmail(String email);
  AppUser grantAppRoleToAppUser(AppUser appUser, AppRole appRole);
  AppUser revokeAppRoleFromAppUser(AppUser appUser, AppRole appRole);
  AppRole saveAppRole(AppRole appRole);
}
