package com.example.gestion_navettes.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser extends AppUser {
  @NotNull(message = "Email est obligatoire !")
  private String firstName;
  
  @NotNull(message = "Mot de passe est obligatoire !")
  private String password;

}
