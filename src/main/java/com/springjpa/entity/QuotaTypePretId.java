package com.springjpa.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuotaTypePretId implements Serializable {
    
    @Column(name = "id_profil")
    private Integer idProfil;
    
    @Column(name = "id_type_pret")
    private Integer idTypePret;
    
    // Constructeurs
    public QuotaTypePretId() {}
    
    public QuotaTypePretId(Integer idProfil, Integer idTypePret) {
        this.idProfil = idProfil;
        this.idTypePret = idTypePret;
    }
    
    // Getters et Setters
    public Integer getIdProfil() {
        return idProfil;
    }
    
    public void setIdProfil(Integer idProfil) {
        this.idProfil = idProfil;
    }
    
    public Integer getIdTypePret() {
        return idTypePret;
    }
    
    public void setIdTypePret(Integer idTypePret) {
        this.idTypePret = idTypePret;
    }
    
    // equals() et hashCode() pour la clé composite
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuotaTypePretId that = (QuotaTypePretId) o;
        return idProfil.equals(that.idProfil) && idTypePret.equals(that.idTypePret);
    }
    
    @Override
    public int hashCode() {
        return 31 * idProfil.hashCode() + idTypePret.hashCode();
    }
}