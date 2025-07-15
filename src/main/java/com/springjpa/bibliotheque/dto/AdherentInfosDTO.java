package com.springjpa.bibliotheque.dto;

import java.util.List;

import com.springjpa.bibliotheque.entity.Abonnement;

public class AdherentInfosDTO {

    private Integer quotaPretDomicile;

    private Integer quotaPretSurPlace;

    private Integer nbPretDomicile;

    private Integer nbPretSurPlace;

    private List<Abonnement> allAbonnement;

    private boolean isAbonne;

    private boolean penalise;
    
    public AdherentInfosDTO(Integer quotaPretDomicile, Integer quotaPretSurPlace, Integer nbPretDomicile,
            Integer nbPretSurPlace, List<Abonnement> allAbonnement, boolean isAbonne, boolean penalise) {
        this.quotaPretDomicile = quotaPretDomicile;
        this.quotaPretSurPlace = quotaPretSurPlace;
        this.nbPretDomicile = nbPretDomicile;
        this.nbPretSurPlace = nbPretSurPlace;
        this.allAbonnement = allAbonnement;
        this.isAbonne = isAbonne;
        this.penalise = penalise;
    }

    public AdherentInfosDTO() {}

    public Integer getQuotaPretDomicile() {
        return quotaPretDomicile;
    }

    public void setQuotaPretDomicile(Integer quotaPretDomicile) {
        this.quotaPretDomicile = quotaPretDomicile;
    }

    public Integer getQuotaPretSurPlace() {
        return quotaPretSurPlace;
    }

    public void setQuotaPretSurPlace(Integer quotaPretSurPlace) {
        this.quotaPretSurPlace = quotaPretSurPlace;
    }

    public Integer getNbPretDomicile() {
        return nbPretDomicile;
    }

    public void setNbPretDomicile(Integer nbPretDomicile) {
        this.nbPretDomicile = nbPretDomicile;
    }

    public Integer getNbPretSurPlace() {
        return nbPretSurPlace;
    }

    public void setNbPretSurPlace(Integer nbPretSurPlace) {
        this.nbPretSurPlace = nbPretSurPlace;
    }

    public List<Abonnement> getAllAbonnement() {
        return allAbonnement;
    }

    public void setAllAbonnement(List<Abonnement> allAbonnement) {
        this.allAbonnement = allAbonnement;
    }

    public boolean isAbonne() {
        return isAbonne;
    }

    public void setAbonne(boolean isAbonne) {
        this.isAbonne = isAbonne;
    }

    public boolean isPenalise() {
        return penalise;
    }

    public void setPenalise(boolean penalise) {
        this.penalise = penalise;
    }

    
}
