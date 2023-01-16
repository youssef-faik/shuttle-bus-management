package com.example.gestion_navettes.security.repositories;

import com.example.gestion_navettes.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppRoleRepository extends JpaRepository<AppRole, Long> {
  AppRole findAppRoleByName(String name);
  
}
