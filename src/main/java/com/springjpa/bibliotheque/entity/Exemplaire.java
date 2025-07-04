package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exemplaire")
public class Exemplaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exemplaire")
    private Integer idExemplaire;
    
    @Column(name = "dispo", nullable = false)
    private boolean dispo = true;
    
    @ManyToOne
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;
    
    // Constructeurs
    public Exemplaire() {
        this.dispo = true; // Valeur par défaut
    }
    
    public Exemplaire(Livre livre, boolean dispo) {
        this.livre = livre;
        this.dispo = dispo;
    }
    
    // Getters et Setters
    public Integer getIdExemplaire() {
        return idExemplaire;
    }
    
    public void setIdExemplaire(Integer idExemplaire) {
        this.idExemplaire = idExemplaire;
    }
    
    public boolean isDispo() {
        return dispo;
    }
    
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }
    
    public Livre getLivre() {
        return livre;
    }
    
    public void setLivre(Livre livre) {
        this.livre = livre;
    }
}