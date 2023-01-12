package com.example.gestion_navette_v1.security.entities;

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
public class LoginUser extends AppUser {
  @NotNull(message = "Email est obligatoire !")
  private String firstName;
  
  @NotNull(message = "Mot de passe est obligatoire !")
  private String password;

}
