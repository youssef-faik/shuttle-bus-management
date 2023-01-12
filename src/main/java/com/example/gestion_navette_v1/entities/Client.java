package com.example.gestion_navette_v1.entities;

import com.example.gestion_navette_v1.security.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends AppUser {
  @NotBlank
  @NotNull(message = "Le prénom du client est obligatoire !")
  @Size(min = 2, max = 80, message = "le prénom de doit être entre 6 et 80 caractères !")
  private String firstName;
  
  @NotBlank
  @NotNull(message = "Le nom du client est obligatoire !")
  @Size(min = 2, max = 80, message = "le nom de doit être entre 6 et 80 caractères !")
  private String lastName;

  @ManyToMany(mappedBy = "clients")
  private List<Offer> subscriptions = new ArrayList<>();
  
  @ManyToMany(mappedBy = "requestingClients")
  private List<Request> subscriptionRequests;
}
