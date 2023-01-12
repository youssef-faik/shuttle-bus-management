package com.example.gestion_navette_v1.security.controllers;

import com.example.gestion_navette_v1.entities.Client;
import com.example.gestion_navette_v1.entities.Company;
import com.example.gestion_navette_v1.security.CustomUserDetails;
import com.example.gestion_navette_v1.security.entities.AppUser;
import com.example.gestion_navette_v1.security.entities.LoginUser;
import com.example.gestion_navette_v1.security.repositories.IAppRoleRepository;
import com.example.gestion_navette_v1.security.repositories.IAppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SecurityController {
  final IAppUserRepository appUserRepository;
  final IAppRoleRepository appRoleRepository;
  final PasswordEncoder passwordEncoder;
  
  
  @GetMapping("/403")
  public String notAuthorized(){
    return "403";
  }
  
  @PostMapping("/new_company")
  public String addNewCompany(@Valid Company company , BindingResult result){
    if (result.hasErrors()) {
      return "/sign_up_company";
    }
    
    company.getRoles().add(appRoleRepository.findAppRoleByName("company"));
    company.setActive(true);
    company.setPassword(passwordEncoder.encode(company.getPassword()));
    appUserRepository.save(company);
  
    authWithoutPassword(company);
    
    return "redirect:/company/my_offers";
  }
  
  @PostMapping("/new_client")
  public String addNewClient(@Valid Client client, BindingResult result) {
    if (result.hasErrors()) {
      return "sign_up_client";
    }
    
    client.getRoles().add(appRoleRepository.findAppRoleByName("client"));
    client.setActive(true);
    client.setPassword(passwordEncoder.encode(client.getPassword()));
    appUserRepository.save(client);
    
    authWithoutPassword(client);
    
    return "redirect:/client/my_subscriptions";
  }
  
  public void authWithoutPassword(AppUser appUser){
    CustomUserDetails userDetails = new CustomUserDetails(appUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
  
  @GetMapping("/login")
  public String getLogin(Model model) {
    LoginUser user = new LoginUser();
    model.addAttribute("user", user);
    
    return "login";
  }
  
   @GetMapping("/signout")
    public String getLogout(Model model) {
//      LoginUser user = new LoginUser();
//      model.addAttribute("user", user);
      
      return "logout";
    }
  
}
