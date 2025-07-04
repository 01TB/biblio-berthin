package com.springjpa.bibliotheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "quota_type_pret")
public class QuotaTypePret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quota_type_pret")
    private Integer idQuotaTypePret;
    
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
        this.profil = profil;
        this.typePret = typePret;
        this.quota = quota;
    }
    
    // Getters et Setters
    public Integer getId() {
        return idQuotaTypePret;
    }
    
    public void setId(Integer idQuotaTypePret) {
        this.idQuotaTypePret = idQuotaTypePret;
    }
    
    public Profil getProfil() {
        return profil;
    }
    
    public void setProfil(Profil profil) {
        this.profil = profil;
    }
    
    public TypePret getTypePret() {
        return typePret;
    }
    
    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }
    
    public Integer getQuota() {
        return quota;
    }
    
    public void setQuota(Integer quota) {
        this.quota = quota;
    }
}