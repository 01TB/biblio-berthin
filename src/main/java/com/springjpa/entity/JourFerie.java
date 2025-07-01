package com.springjpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jour_ferie")
public class JourFerie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jour_ferie")
    private Integer idJourFerie;
    
    @Column(name = "date_ferie", nullable = false)
    private LocalDateTime dateFerie;
    
    // Constructeurs
    public JourFerie() {}
    
    public JourFerie(LocalDateTime dateFerie) {
        this.dateFerie = dateFerie;
    }
    
    // Getters et Setters
    public Integer getIdJourFerie() {
        return idJourFerie;
    }
    
    public void setIdJourFerie(Integer idJourFerie) {
        this.idJourFerie = idJourFerie;
    }
    
    public LocalDateTime getDateFerie() {
        return dateFerie;
    }
    
    public void setDateFerie(LocalDateTime dateFerie) {
        this.dateFerie = dateFerie;
    }
}