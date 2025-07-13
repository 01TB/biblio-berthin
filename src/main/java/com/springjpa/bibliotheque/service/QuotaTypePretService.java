package com.springjpa.bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.QuotaTypePret;
import com.springjpa.bibliotheque.repository.AdherentRepository;
import com.springjpa.bibliotheque.repository.QuotaTypePretRepository;

@Service
public class QuotaTypePretService {
    @Autowired
    private QuotaTypePretRepository quotaTypePretRepository;

    @Autowired
    private PretService pretService;

    @Autowired
    private AdherentRepository adherentRepository;

    public QuotaTypePret findById(Integer id){
        return quotaTypePretRepository.findById(id).get();
    }

    public List<QuotaTypePret> findAll(){
        return quotaTypePretRepository.findAll();
    }

    public void save(QuotaTypePret quotaTypePret){
        quotaTypePretRepository.save(quotaTypePret);
    }

    public QuotaTypePret findByProfilIdProfilAndTypePretIdTypePret(Integer idProfil, Integer idTypePret){
        return quotaTypePretRepository.findByProfilIdProfilAndTypePretIdTypePret(idProfil, idTypePret);
    };

    public boolean depassementQuota(Integer idAdherent, Integer idTypePret){
        Adherent adherent = adherentRepository.findById(idAdherent).orElse(null);
        QuotaTypePret quotaTypePret = findByProfilIdProfilAndTypePretIdTypePret(adherent.getProfil().getIdProfil(), idTypePret);
        long nbPreteEnCours = pretService.comptePretsEnCours(idAdherent, idTypePret);
        return nbPreteEnCours==quotaTypePret.getQuota();
    }
}
