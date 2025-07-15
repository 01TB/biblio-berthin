package com.springjpa.bibliotheque.dto;

public class ExemplaireDisponibiliteDTO {
    private Integer id;
    private String etat;
    private boolean disponible;

    // Constructeurs
    public ExemplaireDisponibiliteDTO() {}
    
    public ExemplaireDisponibiliteDTO(Integer id, String etat, boolean disponible) {
        this.id = id;
        this.etat = etat;
        this.disponible = disponible;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}