package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.bibliotheque.dto.AdherentInfosDTO;
import com.springjpa.bibliotheque.entity.Abonnement;
import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.QuotaTypePret;
import com.springjpa.bibliotheque.entity.TypePret;
import com.springjpa.bibliotheque.service.AbonnementService;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.PenaliteService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.QuotaTypePretService;
import com.springjpa.bibliotheque.service.TypePretService;

@RestController
@RequestMapping("/api/adherents")
public class ApiAdherentController {

    // http://localhost:8081/api/adherents/1/infos

    @Autowired
    private AdherentService adherentService;

    @Autowired
    private AbonnementService abonnementService; 

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private PretService pretService;

    @Autowired
    TypePretService typePretService;

    @Autowired
    QuotaTypePretService quotaTypePretService;

    @GetMapping("/{id}/infos")
    public ResponseEntity<?> getInfosAdherent(@PathVariable Integer id) {
        try {
            Adherent adherent = adherentService.findById(id);
            if (adherent == null) {
                return ResponseEntity.notFound().build();
            }

            List<TypePret> allTypePrets = typePretService.findAll();
            if (allTypePrets.size() < 2) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Données des types de prêt incomplètes");
            }

            TypePret typePretSurPlace = allTypePrets.get(0); // Sur place
            TypePret typePretDomicile = allTypePrets.get(1); // Domicile

            QuotaTypePret quotaSurPlace = quotaTypePretService
                .findByProfilIdProfilAndTypePretIdTypePret(
                    adherent.getProfil().getIdProfil(),
                    typePretSurPlace.getIdTypePret());
            
            QuotaTypePret quotaDomicile = quotaTypePretService
                .findByProfilIdProfilAndTypePretIdTypePret(
                    adherent.getProfil().getIdProfil(),
                    typePretDomicile.getIdTypePret());

            if (quotaSurPlace == null || quotaDomicile == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Données des quotas manquantes");
            }

            long nbPretSurPlace = pretService.comptePretsEnCours(
                adherent.getIdAdherent(),
                typePretSurPlace.getIdTypePret());
            
            long nbPretDomicile = pretService.comptePretsEnCours(
                adherent.getIdAdherent(),
                typePretDomicile.getIdTypePret());

            List<Abonnement> abonnements = abonnementService.findByAdherentMatricule(adherent.getMatricule());
            boolean isAbonne = adherentService.isInscrit(adherent.getMatricule());
            boolean penalise = penaliteService.isPenalise(LocalDateTime.now(), adherent.getIdAdherent());

            AdherentInfosDTO response = new AdherentInfosDTO(
                quotaDomicile.getQuota(), 
                quotaSurPlace.getQuota(), 
                (int)nbPretDomicile, 
                (int)nbPretSurPlace, 
                abonnements, 
                isAbonne, 
                penalise);
                
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur interne: " + e.getMessage());
        }
    }
    
}
