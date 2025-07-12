package com.springjpa.bibliotheque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.Abonnement;
import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.repository.AbonnementRepository;
import com.springjpa.bibliotheque.repository.AdherentRepository;

@Service
public class AdherentService {
    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;


    public Adherent findById(Integer id){
        return adherentRepository.findById(id).get();
    }

    public List<Adherent> findAll(){
        return adherentRepository.findAll();
    }

    public void save(Adherent adherent){
        adherentRepository.save(adherent);
    }

    public Adherent findByMatricule(Integer matriculeAdherent){
        return adherentRepository.findByMatricule(matriculeAdherent);
    };

    public boolean isInscrit(Integer matriculeAdherent) {

        // Récupérer les abonnements de cet adhérent
        List<Abonnement> abonnementsAdherent = abonnementRepository.findByAdherentMatricule(matriculeAdherent);
        // Si l'adhérent n'a aucun abonnement
        if(abonnementsAdherent.isEmpty()) return false;


        for(Abonnement abonnement : abonnementsAdherent) {
            // Si l'intant actuel (NOW) est entre la date de début et la date de fin d'un abonnement
            if(LocalDateTime.now().isAfter(abonnement.getDateDebut()) && LocalDateTime.now().isBefore(abonnement.getDateFin())) return true;
        }
        //  Si aucun abonnement n'inclue l'instant actuel
        return false;        
    }

    public Adherent findByMatriculeAndPassword(Integer matriculeAdherent, String password){
        return adherentRepository.findByMatriculeAndPassword(matriculeAdherent, password);
    }

    public boolean isAdherent(Integer matriculeAdherent, String password){
        return findByMatriculeAndPassword(matriculeAdherent, password)!=null;
    }
}
