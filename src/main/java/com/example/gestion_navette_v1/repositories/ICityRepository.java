package com.example.gestion_navette_v1.repositories;

import com.example.gestion_navette_v1.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICityRepository extends JpaRepository<City, Integer> {
}
