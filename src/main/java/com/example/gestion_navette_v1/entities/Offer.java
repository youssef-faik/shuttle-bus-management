package com.example.gestion_navette_v1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
  
  private String busDescription;
  
  private int desiredNumberOfSubscribers;
  
  @Min(2)
  @Max(1000)
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
