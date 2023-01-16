package com.example.gestion_navettes.repositories;

import com.example.gestion_navettes.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<City, Integer> {
}
