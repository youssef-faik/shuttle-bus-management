package com.example.gestion_navettes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  
  private int id;
  @NotNull(message = "Le nom de la ville est obligatoire !")
  @Size(min = 2, max = 80, message = "le nom de doit être entre 6 et 80 caractères !")
  
  private String name;
}
