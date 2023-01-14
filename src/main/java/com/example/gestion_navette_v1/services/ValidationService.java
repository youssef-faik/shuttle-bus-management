package com.example.gestion_navette_v1.services;

import com.example.gestion_navette_v1.entities.Offer;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ValidationService {
  
  public List<ObjectError> validateOffer(Offer offer) {
    List<ObjectError> errors = new ArrayList<>();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    LocalDate today = LocalDate.parse(dateFormatter.format(new Date()));
    LocalDate startDate = LocalDate.parse(dateFormatter.format(offer.getStartDate()));
    LocalDate endDate = LocalDate.parse(dateFormatter.format(offer.getEndDate()));
    
    Duration duration = Duration.between(
            offer.getDepartureHour().toInstant(),
            offer.getArrivalHour().toInstant());
    
    
    if (offer.getDepartureCity().getId() == offer.getArrivalCity().getId()) {
      errors.add(new ObjectError(
              "city",
              "La ville d'arrivée doit être différente de la ville d'arrivée"));
    }
    
    if (startDate.isBefore(today)) {
      errors.add(new ObjectError(
              "start-date",
              "La date de début doit être supérieure ou égale à la date d'aujourd'hui"));
    }
    
    if (endDate.isBefore(startDate)) {
      errors.add(new ObjectError(
              "end-date",
              "La date de fin doit être supérieure à la date de début"));
    }
    
    if (duration.toMinutes() < 15) {
      errors.add(new ObjectError(
              "arrivale-hour",
              "la durée minimale d'un voyage est de 15 min"));
    }
    
    return errors;
  }
  
}