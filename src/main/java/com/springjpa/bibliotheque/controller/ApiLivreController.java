package com.springjpa.bibliotheque.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.bibliotheque.dto.ExemplaireDisponibiliteDTO;
import com.springjpa.bibliotheque.dto.LivreExemplairesDisponibiliteDTO;
import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Exemplaire;
import com.springjpa.bibliotheque.entity.Livre;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.RetourPret;
import com.springjpa.bibliotheque.service.ExemplaireService;
import com.springjpa.bibliotheque.service.LivreService;
import com.springjpa.bibliotheque.service.PretService;
import com.springjpa.bibliotheque.service.RetourPretService;

@RestController
@RequestMapping("/api/livres")
public class ApiLivreController {

    // http://localhost:8081/api/livres/1/exemplaires-disponibilite

    @Autowired
    private LivreService livreService;
    
    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private PretService pretService;

    @Autowired
    private RetourPretService retourPretService;

    @GetMapping("/{id}/exemplaires-disponibilite")
    public ResponseEntity<LivreExemplairesDisponibiliteDTO> getLivreWithExemplairesDisponibilite(
            @PathVariable Integer id) {
        
        try {
            // Récupérer le livre
            Livre livre = livreService.findById(id);
            
            // Récupérer les exemplaires du livre
            List<Exemplaire> exemplaires = exemplaireService.findByLivreIdLivre(id);
            
            // Dates pour vérification de disponibilité (maintenant + 7 jours)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime inOneWeek = now;

            
            
            // Convertir les exemplaires en DTOs
            List<ExemplaireDisponibiliteDTO> exemplaireDTOs = exemplaires.stream()
                .map(exemplaire -> {
                        List<Pret> allPret = pretService.findByExemplaireIdExemplaire(exemplaire.getIdExemplaire());
                        boolean disponibilite = true;
                        for(Pret pret: allPret) {
                            LocalDateTime datePret = pret.getDateDebut();
                            // LocalDateTime dateFinPret = pretService.getDateFinPret(pret);

                            if(now.isAfter(datePret)) {
                                RetourPret retour = retourPretService.findByPretIdPret(pret.getIdPret());
                                if(retour == null) {
                                    disponibilite = false;
                                }
                            }
                            
                            // Adherent adherent = pret.getAdherent();
                            // if(!exemplaireService.isExemplaireDisponible(exemplaire, adherent, inOneWeek, now)) {
                            //     disponibilite = false;
                            // }
                        }
                        return new ExemplaireDisponibiliteDTO(
                        exemplaire.getIdExemplaire(),
                        null,
                        disponibilite);
                })
                .collect(Collectors.toList());
            
            // Créer la réponse DTO
            LivreExemplairesDisponibiliteDTO response = new LivreExemplairesDisponibiliteDTO(livre, exemplaireDTOs);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}