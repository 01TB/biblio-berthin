package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "profil")
public class Profil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profil")
    private Integer idProfil;
    
    @Column(name = "nom_profil", nullable = false, length = 50)
    private String nomProfil;
    
    @Column(name = "quota_reservation")
    private Integer quotaReservation;

    @Column(name = "quota_prolongation")
    private Integer quotaProlongation;
    
    // Constructeurs
    public Profil() {}
    
    public Profil(String nomProfil, Integer quotaReservation, Integer quotaProlongation) {
        this.nomProfil = nomProfil;
        this.quotaReservation = quotaReservation;
        this.quotaProlongation = quotaProlongation;
    }
    
    // Getters et Setters
    public Integer getIdProfil() {
        return idProfil;
    }
    
    public void setIdProfil(Integer idProfil) {
        this.idProfil = idProfil;
    }
    
    public String getNomProfil() {
        return nomProfil;
    }
    
    public void setNomProfil(String nomProfil) {
        this.nomProfil = nomProfil;
    }
    
    
    public Integer getQuotaReservation() {
        return quotaReservation;
    }
    
    public void setQuotaReservation(Integer quotaReservation) {
        this.quotaReservation = quotaReservation;
    }
    
    public Integer getQuotaProlongation() {
        return quotaProlongation;
    }
    
    public void setQuotaProlongation(Integer quotaProlongation) {
        this.quotaProlongation = quotaProlongation;
    }
}