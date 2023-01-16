package com.example.gestion_navettes.controllers;

import com.example.gestion_navettes.entities.Client;
import com.example.gestion_navettes.entities.Company;
import com.example.gestion_navettes.entities.Offer;
import com.example.gestion_navettes.repositories.ICityRepository;
import com.example.gestion_navettes.repositories.IOfferRepository;
import com.example.gestion_navettes.security.CustomUserDetails;
import com.example.gestion_navettes.security.IAuthenticationFacade;
import com.example.gestion_navettes.security.entities.AppUser;
import com.example.gestion_navettes.security.repositories.IAppRoleRepository;
import com.example.gestion_navettes.security.repositories.IAppUserRepository;
import com.example.gestion_navettes.security.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
  final IOfferRepository subscriptionOfferRepository;
  final ICityRepository cityRepository;
  
  final IAppUserRepository appUserRepository;
  final IAppRoleRepository appRoleRepository;
  final IAuthenticationFacade authenticationFacade;
  final AppUserService appUserService;
  
  
  @GetMapping({"/", "index"})
  public String getHomePage(Model model, Offer searchedOffer) {
    model.addAttribute("cities", cityRepository.findAll());
    model.addAttribute("offer", new Offer());
    
    if (searchedOffer.getDepartureCity() != null && searchedOffer.getArrivalCity() != null) {
      List<Offer> offers;
      if (searchedOffer.getEndDate() != null) {
        offers = subscriptionOfferRepository.findByDepartureCityAndArrivalCityAndStartDateIsGreaterThanEqualAndEndDateIsLessThanEqual(
                searchedOffer.getDepartureCity(),
                searchedOffer.getArrivalCity(),
                searchedOffer.getStartDate(),
                searchedOffer.getEndDate()
        );
        
      } else {
        offers = subscriptionOfferRepository.findOffersByDepartureCityAndArrivalCityAndStartDateIsGreaterThanEqual(
                searchedOffer.getDepartureCity(),
                searchedOffer.getArrivalCity(),
                searchedOffer.getStartDate()
        );
      }
      model.addAttribute("offers", offers);
    } else {
      model.addAttribute("offers", subscriptionOfferRepository.findAll());
    }
    
    return "index";
  }
  
  @GetMapping("/sign_up")
  public String getSignUpClientPage(Model model) {
    model.addAttribute("client", new Client());
    
    return "sign_up_client";
  }
  
  @GetMapping("/sign_up_company")
  public String getSignUpCompnayPage(Model model) {
    model.addAttribute("company", new Company());
    
    return "sign_up_company";
  }
  
  @PostMapping("/login_success")
  public String getLoginSuccessPage() {
    CustomUserDetails userDetails = authenticationFacade.getCustomUserDetails();
    AppUser appUser = appUserService.getUserByEmail(userDetails.getUsername());
    
    if (appUser.getRoles().contains(appRoleRepository.findAppRoleByName("client"))) {
      return "redirect:/client/my_subscriptions";
    }
    
    return "redirect:/company/my_offers";
  }
  
}
