package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "duree_reservation")
public class DureeReservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_duree_reservation")
    private Integer idDureeReservation;
    
    @Column(name = "duree", nullable = false)
    private Integer duree;
    
    @ManyToOne
    @JoinColumn(name = "id_profil", nullable = false)
    private Profil profil;
    
    // Constructeurs
    public DureeReservation() {}
    
    public DureeReservation(Integer duree, Profil profil) {
        this.duree = duree;
        this.profil = profil;
    }
    
    // Getters et Setters
    public Integer getIdDureeReservation() {
        return idDureeReservation;
    }
    
    public void setIdDureeReservation(Integer idDureeReservation) {
        this.idDureeReservation = idDureeReservation;
    }
    
    public Integer getDuree() {
        return duree;
    }
    
    public void setDuree(Integer duree) {
        this.duree = duree;
    }
    
    public Profil getProfil() {
        return profil;
    }
    
    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}