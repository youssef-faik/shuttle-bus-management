package com.example.gestion_navette_v1.security.repositories;

import com.example.gestion_navette_v1.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppRoleRepository extends JpaRepository<AppRole, Long> {
  AppRole findAppRoleByName(String name);
  
}
