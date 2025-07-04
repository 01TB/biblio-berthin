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
    
    public Langue() {}
    
    public Langue(String langue) {
        this.langue = langue;
    }
    
    public Integer getIdLangue() {
        return idLangue;
    }
    
    public void setIdLangue(Integer idLangue) {
        this.idLangue = idLangue;
    }
    
    public String getLangue() {
        return langue;
    }
    
    public void setLangue(String langue) {
        this.langue = langue;
    }
}