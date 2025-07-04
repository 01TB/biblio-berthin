package com.springjpa.bibliotheque.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.TypePret;
import com.springjpa.bibliotheque.repository.PretRepository;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private RetourPretService retourPretService;

    @Autowired
    private DureePretService dureePretService;

    public Pret findById(Integer id){
        return pretRepository.findById(id).get();
    }

    public List<Pret> findAll(){
        return pretRepository.findAll();
    }

    public void save(Pret pret){
        pretRepository.save(pret);
    }

    public List<Pret> findByExemplaireIdExemplaire(Integer idExemplaire){
        return pretRepository.findByExemplaireIdExemplaire(idExemplaire);
    };

    public static boolean datesSeChevauchent(LocalDateTime debut1, LocalDateTime fin1,
                                             LocalDateTime debut2, LocalDateTime fin2) {
        return fin1.isAfter(debut2) && fin2.isAfter(debut1);
    }

    public List<Pret> findByAdherentIdAdherent(Integer idAdherernt){
        return pretRepository.findByAdherentIdAdherent(idAdherernt);
    };

    public long comptePretsEnCours(Integer idAdherent, Integer idTypePret) {
        List<Pret> allPrets = findByAdherentIdAdherent(idAdherent);
        TypePret typePret = new TypePret();
        typePret.setIdTypePret(idTypePret);

        return allPrets.stream()
                .filter(pret -> pret.getTypePret().getIdTypePret().equals(idTypePret))
                .filter(pret -> {
                    LocalDateTime dateFin = getDateFinPret(pret);
                    LocalDateTime now = LocalDateTime.now();
                    return now.isBefore(dateFin) && !retourPretService.existsByPretIdPret(pret.getIdPret());
                })
                .count();
    }

    public LocalDateTime getDateFinPret(Pret pret){
        Profil profilAdherent = pret.getAdherent().getProfil();
        DureePret dureePretAdherent = dureePretService.findByProfilIdProfil(profilAdherent.getIdProfil());
        return pret.getDateDebut().plusDays(dureePretAdherent.getDuree());
    }
}
