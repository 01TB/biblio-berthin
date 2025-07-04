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
@Table(name = "adherent")
public class Adherent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adherent")
    private Integer idAdherent;

    @Column(name = "matricule", nullable = false, unique = true)
    private Integer matricule;
    
    @Column(name = "nom_adherent", length = 50)
    private String nomAdherent;
    
    @Column(name = "prenom_adherent", length = 50)
    private String prenomAdherent;
    
    @Column(name = "password", length = 50)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "id_profil", nullable = false)
    private Profil profil;
    
    // Constructeurs
    public Adherent() {}

    public Adherent(Integer matricule, String nomAdherent, String prenomAdherent, String password, Profil profil) {
        this.matricule = matricule;
        this.nomAdherent = nomAdherent;
        this.prenomAdherent = prenomAdherent;
        this.password = password;
        this.profil = profil;
    }
    
    // Getters et Setters
    public Integer getIdAdherent() {
        return idAdherent;
    }
    
    public void setIdAdherent(Integer idAdherent) {
        this.idAdherent = idAdherent;
    }

    public Integer getMatricule() {
        return matricule;
    }
    
    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }
    
    public String getNomAdherent() {
        return nomAdherent;
    }
    
    public void setNomAdherent(String nomAdherent) {
        this.nomAdherent = nomAdherent;
    }
    
    public String getPrenomAdherent() {
        return prenomAdherent;
    }
    
    public void setPrenomAdherent(String prenomAdherent) {
        this.prenomAdherent = prenomAdherent;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Profil getProfil() {
        return profil;
    }
    
    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}