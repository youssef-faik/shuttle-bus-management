package com.example.gestion_navette_v1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
  
  private boolean open;
  
  @OneToOne
  private Offer suggestedOffer;
  
  
  @ManyToMany
  private List<Client> requestingClients = new ArrayList<>();
  
}
