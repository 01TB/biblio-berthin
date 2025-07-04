package com.springjpa.bibliotheque.entity;

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
@Table(name = "retour_pret")
public class RetourPret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_retour")
    private Integer idRetour;
    
    @Column(name = "date_retour", nullable = false)
    private LocalDateTime dateRetour;
    
    @ManyToOne
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;
    
    // Constructeurs
    public RetourPret() {}
    
    public RetourPret(LocalDateTime dateRetour, Pret pret) {
        this.dateRetour = dateRetour;
        this.pret = pret;
    }
    
    // Getters et Setters
    public Integer getIdRetour() {
        return idRetour;
    }
    
    public void setIdRetour(Integer idRetour) {
        this.idRetour = idRetour;
    }
    
    public LocalDateTime getDateRetour() {
        return dateRetour;
    }
    
    public void setDateRetour(LocalDateTime dateRetour) {
        this.dateRetour = dateRetour;
    }
    
    public Pret getPret() {
        return pret;
    }
    
    public void setPret(Pret pret) {
        this.pret = pret;
    }
}