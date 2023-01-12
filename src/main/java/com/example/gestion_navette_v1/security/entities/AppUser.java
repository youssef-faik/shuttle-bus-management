package com.example.gestion_navette_v1.security.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Email
  @Column(unique = true)
  @NotNull(message = "Email est obligatoire !")
  @Size(min = 2, max = 80, message = "Email doit être entre 6 et 80 caractères !")
  private String email;
  
  @NotNull(message = "Mot de passe est obligatoire !")
  @Size(min = 2, max = 80, message = "le mot de passe de doit être entre 6 et 80 caractères !")
  private String password;
  private boolean active;
  
  @ManyToMany(fetch = FetchType.EAGER)
  private List<AppRole> roles = new ArrayList<>();
}
