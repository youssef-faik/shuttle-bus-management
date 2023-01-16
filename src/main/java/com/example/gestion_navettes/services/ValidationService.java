package com.example.gestion_navettes.services;

import com.example.gestion_navettes.entities.Offer;
import com.example.gestion_navettes.entities.Request;
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
    
    if (offer.getPrice() < 50 || offer.getPrice() > 1000) {
      errors.add(new ObjectError(
              "offer-prix",
              "Le prix d'une abonnement doit être entre 50 et 1000 dirhams !"));
    }
    
    if (offer.getDesiredNumberOfSubscribers() < 1 || offer.getDesiredNumberOfSubscribers() > 100) {
      errors.add(new ObjectError(
              "nombre-desire-abonnees",
              "Le nombre d'abonnées doit être entre 1 et 100 !"));
    }
    
    if (offer.getBusDescription().length() < 30 || offer.getBusDescription().length() > 2000) {
      errors.add(new ObjectError(
              "description-bus",
              "La description de l'autobus doit être entre 30 et 2000 caractères !"));
    }
    
    return errors;
  }
  
  public List<ObjectError> validateRequest(Request request) {
    List<ObjectError> errors = new ArrayList<>();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    LocalDate today = LocalDate.parse(dateFormatter.format(new Date()));
    LocalDate startDate = LocalDate.parse(dateFormatter.format(request.getStartDate()));
    LocalDate endDate = LocalDate.parse(dateFormatter.format(request.getEndDate()));
    
    Duration duration = Duration.between(
            request.getDepartureHour().toInstant(),
            request.getArrivalHour().toInstant());
    
    
    if (request.getDepartureCity().getId() == request.getArrivalCity().getId()) {
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