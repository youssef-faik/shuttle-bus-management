package com.example.gestion_navettes.security.repositories;

import com.example.gestion_navettes.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppUserRepository extends JpaRepository<AppUser, Long> {
  AppUser findAppUserByEmail(String email);
}
