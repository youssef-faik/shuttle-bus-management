package com.example.gestion_navettes.security.services;

import com.example.gestion_navettes.security.CustomUserDetails;
import com.example.gestion_navettes.security.repositories.IAppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
   private final IAppUserRepository appUserRepository;;
   
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     return new CustomUserDetails(appUserRepository.findAppUserByEmail(email));
   }
}
