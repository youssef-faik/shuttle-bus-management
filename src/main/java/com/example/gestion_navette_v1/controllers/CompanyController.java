package com.example.gestion_navette_v1.controllers;

import com.example.gestion_navette_v1.entities.Company;
import com.example.gestion_navette_v1.entities.Offer;
import com.example.gestion_navette_v1.entities.Request;
import com.example.gestion_navette_v1.repositories.ICityRepository;
import com.example.gestion_navette_v1.repositories.IOfferRepository;
import com.example.gestion_navette_v1.repositories.IRequestRepository;
import com.example.gestion_navette_v1.security.CustomUserDetails;
import com.example.gestion_navette_v1.security.IAuthenticationFacade;
import com.example.gestion_navette_v1.security.services.AppUserService;
import com.example.gestion_navette_v1.services.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/company/")
@RequiredArgsConstructor
public class CompanyController {
  final AppUserService appUserService;
  final ICityRepository cityRepository;
  final IOfferRepository offerRepository;
  final IRequestRepository requestRepository;
  final IAuthenticationFacade authenticationFacade;
  final ValidationService validationService;
  
  
  @GetMapping("/my_offers")
  public String getMyOffers(Model model) {
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Company company = (Company) appUserService.getUserByEmail(userDetails.getUsername());
    model.addAttribute("offers", offerRepository.findByOfferingCompany(company));
    
    return "my_offers";
  }
  
  @GetMapping("/subscription_requests")
  public String getSubscriptionRequests(Model model) {
    model.addAttribute("requests", requestRepository.findByOpenTrue());
    
    return "subscription_requests";
  }
  
  @GetMapping("/add_offer")
  public String addOffer(Model model) {
    model.addAttribute("cities", cityRepository.findAll());
    model.addAttribute("offer", new Offer());
    
    return "add_offer";
  }
  
  @PostMapping("/save_offer")
  public String saveOffer(@Valid Offer offer, BindingResult result, Model model) {
    List<ObjectError> errors = validationService.validateOffer(offer);
    
    if (!errors.isEmpty()) {
      for (ObjectError error : errors) {
        result.addError(error);
      }
    }
    
    if (result.hasErrors()) {
      model.addAttribute("cities", cityRepository.findAll());
      
      return "/add_offer";
    }
    
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    offer.setOfferingCompany((Company) appUserService.getUserByEmail(userDetails.getUsername()));
    offerRepository.save(offer);
    
    return "redirect:/company/my_offers";
  }
  
  @PostMapping("/take_up_offer/{id}")
  public String takeUpOffer(@PathVariable("id") Long requestId,
                            @RequestParam(name = "price") float price,
                            @RequestParam(name = "number_additional_people") int numberOfAdditionalPeople,
                            @RequestParam(name = "bus_description") String busDescription
  ) {
    Request request = requestRepository.findById(requestId).get();
    
    if (request != null && request.isOpen()) {
      createOfferFromRequest(request, numberOfAdditionalPeople, price, busDescription);
    }
    
    return "redirect:/company/my_offers";
  }
  
  private void createOfferFromRequest(Request request,
                                      int numberOfAdditionalPeople,
                                      float price,
                                      String busDescription) {
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    Company company = (Company) appUserService.getUserByEmail(userDetails.getUsername());
    
    Offer offer = new Offer();
    offer.setOfferingCompany(company);
    offer.setOpen(true);
    offer.setBusDescription(busDescription);
    
    offer.setDepartureCity(request.getDepartureCity());
    offer.setArrivalCity(request.getArrivalCity());
    
    offer.setDepartureHour(request.getDepartureHour());
    offer.setArrivalHour(request.getArrivalHour());
    
    offer.setStartDate(request.getStartDate());
    offer.setEndDate(request.getStartDate());
    
    offer.setDesiredNumberOfSubscribers(request.getRequestingClients().size() + numberOfAdditionalPeople);
    offer.setPrice(price);
    offer.setSourceRequest(request);
    
    offerRepository.save(offer);
    
    request.setSuggestedOffer(offer);
    request.setOpen(false);
    requestRepository.save(request);
  }
  
}
