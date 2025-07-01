package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "quota_type_pret")
public class QuotaTypePret {
    
    @EmbeddedId
    private QuotaTypePretId id;
    
    @ManyToOne
    @JoinColumn(name = "id_profil", insertable = false, updatable = false)
    private Profil profil;
    
    @ManyToOne
    @JoinColumn(name = "id_type_pret", insertable = false, updatable = false)
    private TypePret typePret;
    
    @Column(name = "quota", nullable = false)
    private Integer quota;
    
    // Constructeurs
    public QuotaTypePret() {}
    
    public QuotaTypePret(Profil profil, TypePret typePret, Integer quota) {
        this.id = new QuotaTypePretId(profil.getIdProfil(), typePret.getIdTypePret());
        this.profil = profil;
        this.typePret = typePret;
        this.quota = quota;
    }
    
    // Getters et Setters
    public QuotaTypePretId getId() {
        return id;
    }
    
    public void setId(QuotaTypePretId id) {
        this.id = id;
    }
    
    public Profil getProfil() {
        return profil;
    }
    
    public void setProfil(Profil profil) {
        this.profil = profil;
        if (profil != null && this.id != null) {
            this.id.setIdProfil(profil.getIdProfil());
        }
    }
    
    public TypePret getTypePret() {
        return typePret;
    }
    
    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
        if (typePret != null && this.id != null) {
            this.id.setIdTypePret(typePret.getIdTypePret());
        }
    }
    
    public Integer getQuota() {
        return quota;
    }
    
    public void setQuota(Integer quota) {
        this.quota = quota;
    }
}