package com.springjpa.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.entity.Adherent;
import com.springjpa.entity.QuotaTypePret;
import com.springjpa.repository.QuotaTypePretRepository;

@Service
public class QuotaTypePretService {
    @Autowired
    private QuotaTypePretRepository quotaTypePretRepository;

    @Autowired
    private PretService pretService;

    @Autowired
    private AdherentService adherentService;

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
        Adherent adherent = adherentService.findById(idAdherent);
        QuotaTypePret quotaTypePret = findByProfilIdProfilAndTypePretIdTypePret(adherent.getProfil().getIdProfil(), idTypePret);
        long nbPreteEnCours = pretService.comptePretsEnCours(idAdherent, idTypePret);
        return nbPreteEnCours==quotaTypePret.getQuota();
    }
}
