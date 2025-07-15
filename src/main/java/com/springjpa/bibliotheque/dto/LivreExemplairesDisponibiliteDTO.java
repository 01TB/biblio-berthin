package com.springjpa.bibliotheque.dto;

import com.springjpa.bibliotheque.entity.Livre;
import java.util.List;

public class LivreExemplairesDisponibiliteDTO {
    private Livre livre;
    private List<ExemplaireDisponibiliteDTO> exemplaires;

    // Constructeurs
    public LivreExemplairesDisponibiliteDTO() {}
    
    public LivreExemplairesDisponibiliteDTO(Livre livre, List<ExemplaireDisponibiliteDTO> exemplaires) {
        this.livre = livre;
        this.exemplaires = exemplaires;
    }

    // Getters et Setters
    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public List<ExemplaireDisponibiliteDTO> getExemplaires() {
        return exemplaires;
    }

    public void setExemplaires(List<ExemplaireDisponibiliteDTO> exemplaires) {
        this.exemplaires = exemplaires;
    }
}