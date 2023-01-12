package com.example.gestion_navette_v1.security.services;

import com.example.gestion_navette_v1.security.entities.AppUser;
import com.example.gestion_navette_v1.security.repositories.IAppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {
  final IAppUserRepository appUserRepository;
  
  public AppUser getUserByEmail(String email){
    return appUserRepository.findAppUserByEmail(email);
  }

}
