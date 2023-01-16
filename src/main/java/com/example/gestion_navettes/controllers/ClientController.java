package com.example.gestion_navettes.controllers;

import com.example.gestion_navettes.entities.Client;
import com.example.gestion_navettes.entities.Offer;
import com.example.gestion_navettes.entities.Request;
import com.example.gestion_navettes.repositories.ICityRepository;
import com.example.gestion_navettes.repositories.IOfferRepository;
import com.example.gestion_navettes.repositories.IRequestRepository;
import com.example.gestion_navettes.security.CustomUserDetails;
import com.example.gestion_navettes.security.IAuthenticationFacade;
import com.example.gestion_navettes.security.repositories.IAppUserRepository;
import com.example.gestion_navettes.security.services.AppUserService;
import com.example.gestion_navettes.services.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/client/")
@RequiredArgsConstructor
public class ClientController {
  final AppUserService appUserService;
  final ICityRepository cityRepository;
  final IAuthenticationFacade authenticationFacade;
  final IOfferRepository offerRepository;
  final IRequestRepository requestRepository;
  final IAppUserRepository appUserRepository;
  final ValidationService validationService;
  
  @GetMapping("/my_subscriptions")
  public String getMySubscriptions(Model model){
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
    
    model.addAttribute("subscriptions", offerRepository.findByClientsIs(client));
  
    return "my_subscriptions";
  }
  
  @GetMapping("/my_requests")
  public String getMyRequests(Model model){
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
    model.addAttribute("requests", requestRepository.findByRequestingClientsEquals(client));
    model.addAttribute("offers", offerRepository.findAll());
    
    return "my_requests";
  }
  
  @GetMapping("/add_request")
  public String addSubscriptionRequest(Model model){
    model.addAttribute("cities", cityRepository.findAll());
    model.addAttribute("request", new Request());
    
    return "add_request";
  }
  
  @PostMapping("/save_request")
  public String saveOffer(@Valid Request request, BindingResult result, Model model){
    List<ObjectError> errors = validationService.validateRequest(request);
  
    if (!errors.isEmpty()) {
      for (ObjectError error : errors) {
        result.addError(error);
      }
    }
  
    if (result.hasErrors()) {
      model.addAttribute("cities", cityRepository.findAll());
    
      return "/add_request";
    }
    
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
    
    Request matchingRequest = requestRepository.findByDepartureCityAndArrivalCity(
            request.getDepartureCity(),
            request.getArrivalCity());
    
    if (matchingRequest != null) {
      if (!matchingRequest.getRequestingClients().contains(client)) {
        matchingRequest.getRequestingClients().add(client);
        requestRepository.save(matchingRequest);
      }
      return "redirect:/client/my_requests";
    }
    
    request.getRequestingClients().add((client));
    request.setOpen(true);
    requestRepository.save(request);
    
    return "redirect:/client/my_requests";
  }
  
  @PostMapping("/subscribe/{id}")
  public String subscribe(@PathVariable("id") Long offerId){
    Offer offer = offerRepository.findById(offerId).get();
    
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Client client = (Client) appUserService.getUserByEmail(userDetails.getUsername());
  
    if (!offer.getClients().contains(client)
            && offer.getClients().size() < offer.getDesiredNumberOfSubscribers()) {
      offer.getClients().add(client);
      offerRepository.save(offer);
    }
    
    return "redirect:/client/my_subscriptions";
  }
  
 
}
