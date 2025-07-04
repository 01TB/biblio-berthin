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
@Table(name = "prolongation_pret")
public class ProlongationPret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prolongation")
    private Integer idProlongation;
    
    @Column(name = "date_prolongation", nullable = false)
    private LocalDateTime dateProlongation;
    
    @ManyToOne
    @JoinColumn(name = "id_pret", nullable = false)
    private Pret pret;
    
    // Constructeurs
    public ProlongationPret() {}
    
    public ProlongationPret(LocalDateTime dateProlongation, Pret pret) {
        this.dateProlongation = dateProlongation;
        this.pret = pret;
    }
    
    // Getters et Setters
    public Integer getIdProlongation() {
        return idProlongation;
    }
    
    public void setIdProlongation(Integer idProlongation) {
        this.idProlongation = idProlongation;
    }
    
    public LocalDateTime getDateProlongation() {
        return dateProlongation;
    }
    
    public void setDateProlongation(LocalDateTime dateProlongation) {
        this.dateProlongation = dateProlongation;
    }
    
    public Pret getPret() {
        return pret;
    }
    
    public void setPret(Pret pret) {
        this.pret = pret;
    }
}