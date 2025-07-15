package com.springjpa.bibliotheque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.ProlongationPret;
import com.springjpa.bibliotheque.entity.TypePret;
import com.springjpa.bibliotheque.repository.DureePretRepository;
import com.springjpa.bibliotheque.repository.ProlongationPretRepository;

@Service
public class ProlongationPretService {

    @Autowired
    private ProlongationPretRepository prolongationPretRepository;

    @Autowired
    private DureePretRepository dureePretRepository;

    public ProlongationPret findById(Integer id){
        return prolongationPretRepository.findById(id).get();
    }

    public List<ProlongationPret> findAll(){
        return prolongationPretRepository.findAll();
    }

    public List<ProlongationPret> findByPretIdPret(Integer idPret) {
        return prolongationPretRepository.findByPretIdPret(idPret);
    }

    public void save(ProlongationPret prolongationPret){
        prolongationPretRepository.save(prolongationPret);
    }

    public List<ProlongationPret> findByPretAdherentIdAdherent(Integer idAdherent) {
        return prolongationPretRepository.findByPretAdherentIdAdherent(idAdherent);
    };

    public long compteProlongationEnCours(Integer idAdherent, LocalDateTime dateProlongation) {
        List<ProlongationPret> allProlongation = findByPretAdherentIdAdherent(idAdherent);
        return allProlongation.stream()
                .filter(prolongation -> {
                    Pret pret = prolongation.getPret();
                    Profil profil = pret.getAdherent().getProfil();
                    DureePret dureePret = dureePretRepository.findByProfilIdProfil(profil.getIdProfil());
                    LocalDateTime dateFinProlongation = prolongation.getDateProlongation().plusDays(dureePret.getDuree());
                    return prolongation.getDateProlongation().isBefore(dateProlongation) && dateFinProlongation.isAfter(dateProlongation);
                })
                .count();
    }
}