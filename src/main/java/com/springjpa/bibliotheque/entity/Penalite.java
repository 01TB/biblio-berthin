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
@Table(name = "penalite")
public class Penalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_penalite")
    private Integer idPenalite;

    @Column(name = "date_penalite", nullable = false)
    private LocalDateTime datePenalite;

    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    // Constructeurs
    public Penalite() {}

    public Penalite(LocalDateTime datePenalite, Adherent adherent) {
        this.datePenalite = datePenalite;
        this.adherent = adherent;
    }

    // Getters et setters
    public Integer getIdPenalite() {
        return idPenalite;
    }

    public void setIdPenalite(Integer idPenalite) {
        this.idPenalite = idPenalite;
    }

    public LocalDateTime getDatePenalite() {
        return datePenalite;
    }

    public void setDatePenalite(LocalDateTime datePenalite) {
        this.datePenalite = datePenalite;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }
}