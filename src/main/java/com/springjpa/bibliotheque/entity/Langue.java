package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "langue")
public class Langue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_langue")
    private Integer idLangue;
    
    @Column(name = "langue", nullable = false, length = 50)
    private String langue;
    
    // Constructeurs
    public Langue() {}
    
    public Langue(String langue) {
        this.langue = langue;
    }
    
    // Getters et Setters
    public Integer getIdLangue() {
        return idLangue;
    }
    
    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }
    
    public String getNomLangue() {
        return langue;
    }
    
    public void setNomLangue(String langue) {
        this.langue = langue;
    }
}