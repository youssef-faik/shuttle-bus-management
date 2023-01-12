package com.example.gestion_navette_v1;

import com.example.gestion_navette_v1.entities.City;
import com.example.gestion_navette_v1.entities.Offer;
import com.example.gestion_navette_v1.repositories.IOfferRepository;
import com.example.gestion_navette_v1.security.entities.AppRole;
import com.example.gestion_navette_v1.entities.Company;
import com.example.gestion_navette_v1.entities.Client;
import com.example.gestion_navette_v1.security.repositories.IAppRoleRepository;
import com.example.gestion_navette_v1.security.repositories.IAppUserRepository;
import com.example.gestion_navette_v1.repositories.ICityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class GestionNavetteV1Application implements CommandLineRunner {
  final IAppRoleRepository appRoleRepository;
  final IAppUserRepository appUserRepository;
  final ICityRepository cityRepository;
  final PasswordEncoder passwordEncoder;
  final IOfferRepository subscriptionOfferRepository;
  
  public static void main(String[] args) {
    SpringApplication.run(GestionNavetteV1Application.class, args);
  }
  
  @Override
  public void run(String... args) throws Exception {
    initializeCityTable();
    initializeRoleTable();
    initializeUsersTable();
//    initializeOfferTable();
  
  }
  
  private void initializeOfferTable() {
    List<Client> clients = Arrays.asList(
            (Client) appUserRepository.findAppUserByEmail("said@mail.com"),
            (Client) appUserRepository.findAppUserByEmail("talis@mail.com")
    );
    
    Offer offer = new Offer(null, "", 5, 99, true, (Company) appUserRepository.findAppUserByEmail("ghazalla@mail.com"), cityRepository.findById(1).get(), cityRepository.findById(3).get(), new Date(), new Date(), new Date(), new Date(), null, clients);
    subscriptionOfferRepository.save(offer);
  }
  
  private void initializeRoleTable() {
    AppRole roleClient = new AppRole(null, "client");
    AppRole roleCompany = new AppRole(null, "company");
    
    if (appRoleRepository.findAppRoleByName("client") == null) {
      appRoleRepository.save(roleClient);
    }
    ;
    
    if (appRoleRepository.findAppRoleByName("company") == null) {
      appRoleRepository.save(roleCompany);
    }
  }
  
  private void initializeUsersTable() {
    if (appUserRepository.findAppUserByEmail("said@mail.com") == null) {
      Client said = new Client();
      said.getRoles().add(appRoleRepository.findAppRoleByName("client"));
      said.setActive(true);
      said.setFirstName("Said");
      said.setLastName("Alami");
      said.setEmail("said@mail.com");
      said.setPassword(passwordEncoder.encode("8765153$$^%$%hjgvj"));
  
      appUserRepository.save(said);
    }
    
    if (appUserRepository.findAppUserByEmail("talis@mail.com") == null) {
      Client talis = new Client();
      talis.getRoles().add(appRoleRepository.findAppRoleByName("client"));
      talis.setActive(true);
      talis.setFirstName("talis");
      talis.setLastName("Najib");
      talis.setEmail("talis@mail.com");
      talis.setPassword(passwordEncoder.encode("8765153$$^%$%hjgvj"));
  
      appUserRepository.save(talis);
    }
    
    if (appUserRepository.findAppUserByEmail("stcr@mail.com") == null) {
      Company stcr = new Company("stcr");
      stcr.getRoles().add(appRoleRepository.findAppRoleByName("company"));
      stcr.setActive(true);
      stcr.setEmail("stcr@mail.com");
      stcr.setPassword(passwordEncoder.encode("8765153$$^%$%hjgvj"));
  
      appUserRepository.save(stcr);
    }
    
    if (appUserRepository.findAppUserByEmail("ghazalla@mail.com") == null) {
      Company ghazala = new Company();
      ghazala.getRoles().add(appRoleRepository.findAppRoleByName("company"));
      ghazala.setActive(true);
      ghazala.setName("Ghazalla");
      ghazala.setEmail("ghazalla@mail.com");
      ghazala.setPassword(passwordEncoder.encode("8765153$$^%$%hjgvj"));
  
      appUserRepository.save(ghazala);
    }
    
    
  }
  
  private void initializeCityTable() {
    if (cityRepository.findAll().size() == 0 ) {
      String[] ciytNames = {
              "Al Hoceima",
              "Béni Mellal",
              "Casablanca",
              "Dakhla-Oued",
              "Drâa",
              "Ed-Dahab",
              "Fès",
              "Guelmim",
              "Khénifra",
              "Kénitra",
              "Laâyoune",
              "Marrakesh",
              "Massa",
              "Meknès",
              "Oued Noun",
              "Rabat",
              "Safi",
              "Sakia El Hamra",
              "Salé",
              "Settat",
              "Souss",
              "Tafilalet",
              "Tanger",
              "Tetouan"
      };
      
      for(String name:ciytNames){
        City city = new City();
        city.setName(name);
        cityRepository.save(city);
      }
    }
  }
}
