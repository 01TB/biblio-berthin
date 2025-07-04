
package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Integer idReservation;
    
    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;

    @Column(name = "date_expiration", nullable = false)
    private LocalDateTime dateExpiration;
    
    @ManyToOne
    @JoinColumn(name = "id_statut", nullable = false)
    private StatutReservation statut;
    
    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;
    
    // Constructeurs
    public Reservation() {}
    
    public Reservation(LocalDateTime dateReservation, LocalDateTime dateExpiration, 
                       StatutReservation statut, Exemplaire exemplaire, 
                       Adherent adherent) {
        this.dateReservation = dateReservation;
        this.statut = statut;
        this.exemplaire = exemplaire;
        this.adherent = adherent;
    }
    
    // Getters et Setters
    public Integer getIdReservation() {
        return idReservation;
    }
    
    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }
    
    public LocalDateTime getDateReservation() {
        return dateReservation;
    }
    
    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }
    
    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    
    public StatutReservation getStatut() {
        return statut;
    }
    
    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }
    
    public Exemplaire getExemplaire() {
        return exemplaire;
    }
    
    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }
    
    public Adherent getAdherent() {
        return adherent;
    }
    
    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
}
