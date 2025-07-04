package com.springjpa.bibliotheque.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livre")
public class Livre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livre")
    private Integer idLivre;
    
    @Column(name = "titre", length = 50, nullable = false)
    private String titre;
    
    @Column(name = "isbn", length = 255, nullable = false, unique = true)
    private String isbn;
    
    @Column(name = "annee_publication", nullable = false)
    private Integer anneePublication;
    
    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;
    
    @Column(name = "nb_page")
    private Integer nbPage;
    
    @ManyToOne
    @JoinColumn(name = "id_editeur", nullable = false)
    private Editeur editeur;
    
    @ManyToOne
    @JoinColumn(name = "id_auteur", nullable = false)
    private Auteur auteur;
    
    @ManyToOne
    @JoinColumn(name = "id_langue", nullable = false)
    private Langue langue;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "categorie_livre",
        joinColumns = @JoinColumn(name = "id_livre", referencedColumnName = "id_livre"),
        inverseJoinColumns = @JoinColumn(name = "id_categorie", referencedColumnName = "id_categorie")
    )
    private Set<Categorie> categories = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "accessibilite_profil_livre",
        joinColumns = @JoinColumn(name = "id_livre", referencedColumnName = "id_livre"),
        inverseJoinColumns = @JoinColumn(name = "id_profil", referencedColumnName = "id_profil")
    )
    private Set<Profil> profils = new HashSet<>();

    public Livre() {}
    
    public Livre(String titre, String isbn, Integer anneePublication, String synopsis, 
                 Integer nbPage, Editeur editeur, Auteur auteur, Langue langue) {
        this.titre = titre;
        this.isbn = isbn;
        this.anneePublication = anneePublication;
        this.synopsis = synopsis;
        this.nbPage = nbPage;
        this.editeur = editeur;
        this.auteur = auteur;
        this.langue = langue;
    }
    
    public Integer getIdLivre() {
        return idLivre;
    }
    
    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Integer getAnneePublication() {
        return anneePublication;
    }
    
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }
    
    public String getSynopsis() {
        return synopsis;
    }
    
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    public Integer getNbPage() {
        return nbPage;
    }
    
    public void setNbPage(Integer nbPage) {
        this.nbPage = nbPage;
    }
    
    public Editeur getEditeur() {
        return editeur;
    }
    
    public void setEditeur(Editeur editeur) {
        this.editeur = editeur;
    }
    
    public Auteur getAuteur() {
        return auteur;
    }
    
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }
    
    public Langue getLangue() {
        return langue;
    }
    
    public void setLangue(Langue langue) {
        this.langue = langue;
    }
    
    public Set<Categorie> getCategories() {
        return categories;
    }
    
    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Set<Profil> getProfils() {
        return profils;
    }

    public void setProfils(Set<Profil> profils) {
        this.profils = profils;
    }

    public boolean peutPreter(Profil profil) {
        return profils.contains(profil);
    }
}