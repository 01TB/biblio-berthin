package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Penalite;
import com.springjpa.bibliotheque.entity.TypePenaliteProfil;
import com.springjpa.bibliotheque.repository.AdherentRepository;
import com.springjpa.bibliotheque.repository.PenaliteRepository;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private TypePenaliteProfilService typePenaliteProfilService;

    public Penalite findById(Integer id){
        return penaliteRepository.findById(id).get();
    }

    public List<Penalite> findAll(){
        return penaliteRepository.findAll();
    }

    public void save(Penalite penalite){
        penaliteRepository.save(penalite);
    }

    public List<Penalite> findByAdherentidAdherent(Integer idAdherent) {
        return penaliteRepository.findByAdherentIdAdherent(idAdherent);
    }

    public List<Penalite> findByAdherent(Adherent adherent) {
        return penaliteRepository.findByAdherent(adherent);
    }

    public boolean isPenalise(LocalDateTime currentDate, Integer idAdherent) {
        Adherent adherent = adherentRepository.findById(idAdherent).orElse(null);
        if (adherent == null) return false;

        List<Penalite> penalties = findByAdherent(adherent)
            .stream()
            .sorted((p1, p2) -> p1.getDatePenalite().compareTo(p2.getDatePenalite()))
            .collect(Collectors.toList());

        LocalDateTime lastPenaltyEnd = null;

        for (Penalite penalty : penalties) {
            TypePenaliteProfil typePenalite = typePenaliteProfilService.findByProfilIdProfil(adherent.getProfil().getIdProfil());
            long penaltyDuration = typePenalite.getDuree();
            LocalDateTime penaltyEnd = penalty.getDatePenalite().plusDays(penaltyDuration);

            if (lastPenaltyEnd == null) {
                if (currentDate.isBefore(penaltyEnd)) {
                    return true;
                }
                lastPenaltyEnd = penaltyEnd;
            } else {
                if (currentDate.isBefore(lastPenaltyEnd)) {
                    return true;
                }
                if (currentDate.isAfter(lastPenaltyEnd) && currentDate.isBefore(penaltyEnd)) {
                    lastPenaltyEnd = penaltyEnd;
                }
            }
        }
        return false;
    }
}