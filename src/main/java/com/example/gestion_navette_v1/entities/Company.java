package com.example.gestion_navette_v1.entities;

import com.example.gestion_navette_v1.security.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends AppUser {
  
  @NotNull(message = "Le nom de la société est obligatoire !")
  @Size(min = 2, max = 200, message = "le nom de la société doit être entre 6 et 200 caractères !")
  private String name;
}
