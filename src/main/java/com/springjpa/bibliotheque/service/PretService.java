package com.springjpa.bibliotheque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.bibliotheque.entity.DureePret;
import com.springjpa.bibliotheque.entity.JourFerie;
import com.springjpa.bibliotheque.entity.Pret;
import com.springjpa.bibliotheque.entity.Profil;
import com.springjpa.bibliotheque.entity.ProlongationPret;
import com.springjpa.bibliotheque.entity.TypePret;
import com.springjpa.bibliotheque.repository.DureePretRepository;
import com.springjpa.bibliotheque.repository.PretRepository;

@Service
public class PretService {

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private RetourPretService retourPretService;

    @Autowired
    private DureePretRepository dureePretRepository;

    @Autowired
    private ProlongationPretService prolongationPretService;

    @Autowired
    private JourFerieService jourFerieService;

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

    // Décalage de 1 jour si la date chevauche un jour férié
    public LocalDateTime decalageDeUnJour(LocalDateTime date) {
        List<JourFerie> allJourFeries = jourFerieService.findAll();
        for(JourFerie jourFerie : allJourFeries) {
            boolean sameDayMonthYear = date.getDayOfMonth() == jourFerie.getDateFerie().getDayOfMonth() 
                                        && date.getMonth() == jourFerie.getDateFerie().getMonth()
                                        && date.getYear() == jourFerie.getDateFerie().getYear();
            if(sameDayMonthYear) {
                return date.plusDays(1);
            }
        }
        return date;
    }

    public LocalDateTime getDateFinPret(Pret pret){
        Profil profilAdherent = pret.getAdherent().getProfil();
        DureePret dureePretAdherent = dureePretRepository.findByProfilIdProfil(profilAdherent.getIdProfil());
        List<ProlongationPret> prolongations = prolongationPretService.findByPretIdPret(pret.getIdPret());

        // Sans prolongation
        if(prolongations.isEmpty()){

            LocalDateTime finDatePret = pret.getDateDebut().plusDays(dureePretAdherent.getDuree());
            
            // Décalage de 1 jour de la date de retour de prêt si la date chevauche un jour férié
            List<JourFerie> allJourFeries = jourFerieService.findAll();
            if(!allJourFeries.isEmpty()) {
                finDatePret = decalageDeUnJour(finDatePret);
            }

            return finDatePret;
        }

        // En cas de prolongations
        LocalDateTime finPret = prolongations.get(0).getDateProlongation().plusDays(dureePretAdherent.getDuree());    // Première prolongation
        for(ProlongationPret prolongationPret : prolongations) {
            if(finPret.isBefore(prolongationPret.getDateProlongation().plusDays(dureePretAdherent.getDuree()))) {
                finPret = prolongationPret.getDateProlongation().plusDays(dureePretAdherent.getDuree());
            }
        }
            
        // Décalage de 1 jour de la date de retour de prêt (avec prolongation) si la date chevauche un jour férié
        List<JourFerie> allJourFeries = jourFerieService.findAll();
        if(!allJourFeries.isEmpty()) {
            finPret = decalageDeUnJour(finPret);
        }

        return finPret;
    }
}
