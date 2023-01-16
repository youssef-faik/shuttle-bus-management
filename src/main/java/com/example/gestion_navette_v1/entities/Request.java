package com.example.gestion_navette_v1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotNull(message = "La ville de depart est obligatoire !")
  @OneToOne
  private City departureCity;
  
  @NotNull(message = "La ville d'arrive est obligatoire !")
  @OneToOne
  private City arrivalCity;
  
  @NotNull(message = "La date de debut est obligatoire !")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;
  
  @NotNull(message = "La date fin est obligatoire !")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endDate;
  
  @NotNull(message = "L'heure de depart est obligatoire !")
  @DateTimeFormat(pattern = "HH:mm")
  private Date departureHour;
  
  @NotNull(message = "L'heure d'arrive est obligatoire !")
  @DateTimeFormat(pattern = "HH:mm")
  private Date arrivalHour;
  
  private boolean open;
  
  @OneToOne
  private Offer suggestedOffer;
  
  
  @ManyToMany
  private List<Client> requestingClients = new ArrayList<>();
  
}
