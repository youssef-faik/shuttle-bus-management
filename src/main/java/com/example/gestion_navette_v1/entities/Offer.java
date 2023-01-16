package com.example.gestion_navette_v1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Offer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotNull(message = "La description de l'autobus est obligatoire !")
  @NotBlank(message = "La description de l'autobus est obligatoire !")
  @Size(min = 30, max = 2000, message = "La description de l'autobus doit être entre 30 et 2000 caractères !")
  private String busDescription;
  
  @Min(value = 1, message = "Le nombre minimale d'abonnées est 1 !")
  @Max(value = 100, message = "Le nombre maximale d'abonnées est 100 !")
  private int desiredNumberOfSubscribers;
  
  @Min(value = 50, message = "Le prix minimale d'une abonnement est 50 dirhams !")
  @Max(value = 1000, message = "Le prix maximale d'une abonnement est 1000 dirhams !")
  private float price;
  
  private boolean open;
  
  @OneToOne
  private Company offeringCompany;
  
  @OneToOne
  private City departureCity;
  
  @OneToOne
  private City arrivalCity;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;
  
  @DateTimeFormat(pattern = "HH:mm")
  private Date departureHour;
  
  @DateTimeFormat(pattern = "HH:mm")
  private Date arrivalHour;
  
  @OneToOne
  private Request sourceRequest;
  
  @ManyToMany
  private List<Client> clients = new ArrayList<>();
}
