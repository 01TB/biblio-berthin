
package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "editeur")
public class Editeur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editeur")
    private Integer idEditeur;
    
    @Column(name = "nom_editeur", nullable = false, length = 50)
    private String nomEditeur;
    
    @Column(name = "localisation", length = 50)
    private String localisation;
    
    // Constructeurs
    public Editeur() {}
    
    public Editeur(String nomEditeur, String localisation) {
        this.nomEditeur = nomEditeur;
        this.localisation = localisation;
    }
    
    // Getters et Setters
    public Integer getIdEditeur() {
        return idEditeur;
    }
    
    public void setIdEditeur(Integer idEditeur) {
        this.idEditeur = idEditeur;
    }
    
    public String getNomEditeur() {
        return nomEditeur;
    }
    
    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }
    
    public String getLocalisation() {
        return localisation;
    }
    
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
