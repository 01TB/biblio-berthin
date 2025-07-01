package com.springjpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "abonnement")
public class Abonnement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abonnement")
    private Integer idAbonnement;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;
    
    // Constructeurs
    public Abonnement() {}
    
    public Abonnement(LocalDateTime dateDebut, LocalDateTime dateFin, 
                       Adherent adherent) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.adherent = adherent;
    }
    
    // Getters et Setters
    public Integer getIdAbonnement() {
        return idAbonnement;
    }
    
    public void setIdAbonnement(Integer idAbonnement) {
        this.idAbonnement = idAbonnement;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
    
    public Adherent getAdherent() {
        return adherent;
    }
    
    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
}