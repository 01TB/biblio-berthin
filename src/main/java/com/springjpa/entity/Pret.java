package com.springjpa.entity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.springjpa.service.DureePretService;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pret")
public class Pret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pret")
    private Integer idPret;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;
    
    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;
    
    @ManyToOne
    @JoinColumn(name = "id_type_pret", nullable = false)
    private TypePret typePret;
    
    @ManyToOne
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "id_adherent", nullable = false)
    private Adherent adherent;

    @Autowired
    private DureePretService dureePretService;
    
    // Constructeurs
    public Pret() {}
    
    public Pret(LocalDateTime dateDebut, Admin admin, 
                TypePret typePret, Exemplaire exemplaire, Adherent adherent) {
        this.dateDebut = dateDebut;
        this.admin = admin;
        this.typePret = typePret;
        this.exemplaire = exemplaire;
        this.adherent = adherent;
    }
    
    // Getters et Setters
    public Integer getIdPret() {
        return idPret;
    }
    
    public void setIdPret(Integer idPret) {
        this.idPret = idPret;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public TypePret getTypePret() {
        return typePret;
    }
    
    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
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

    public LocalDateTime getDateFinPret(){
        Profil profilAdherent = this.getAdherent().getProfil();
        DureePret dureePretAdherent = dureePretService.findByProfilIdProfil(profilAdherent.getIdProfil());
        return this.getDateDebut().plusDays(dureePretAdherent.getDuree());
    }
}