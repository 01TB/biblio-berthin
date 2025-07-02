package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.Abonnement;
import com.springjpa.entity.Adherent;
import com.springjpa.repository.AbonnementRepository;
import com.springjpa.repository.AdherentRepository;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private PenaliteService penaliteService;

    public Adherent findById(Integer id){
        return adherentRepository.findById(id).get();
    }

    public List<Adherent> findAll(){
        return adherentRepository.findAll();
    }

    public void save(Adherent adherent){
        adherentRepository.save(adherent);
    }

    public boolean isInscrit(Integer adherentId) {
        var adherentOpt = adherentRepository.findById(adherentId);
        if (adherentOpt.isEmpty()) return false;

        var adherant = adherentOpt.get();
        // Récupérer les abonnements de cet adhérent
        List<Abonnement> abonnementsAdherent = abonnementService.findByAdherentId(adherentId);
        // Si l'adhérent n'a aucun abonnement
        if(abonnementsAdherent.isEmpty()) return false;


        for(Abonnement abonnement : abonnementsAdherent) {
            // Si l'intant actuel (NOW) est entre la date de début et la date de fin d'un abonnement
            if(LocalDateTime.now().isAfter(abonnement.getDateDebut()) && LocalDateTime.now().isBefore(abonnement.getDateFin())) return true;
        }
        //  Si aucun abonnement n'inclue l'instant actuel
        return false;        
    }
}
