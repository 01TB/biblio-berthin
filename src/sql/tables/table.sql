CREATE TABLE auteur(
   id_auteur INT AUTO_INCREMENT,
   nom_auteur VARCHAR(50) NOT NULL,
   prenom_auteur VARCHAR(50),
   PRIMARY KEY(id_auteur)
);

CREATE TABLE editeur(
   id_editeur INT AUTO_INCREMENT,
   nom_editeur VARCHAR(50) NOT NULL,
   localisation VARCHAR(50),
   PRIMARY KEY(id_editeur)
);

CREATE TABLE categorie(
   id_categorie INT AUTO_INCREMENT,
   nom_categorie VARCHAR(255) NOT NULL,
   PRIMARY KEY(id_categorie)
);

CREATE TABLE profil(
   id_profil INT AUTO_INCREMENT,
   nom_profil VARCHAR(255) NOT NULL,
   quota_reservation INT NOT NULL,
   quota_prolongation INT NOT NULL,
   PRIMARY KEY(id_profil)
);

CREATE TABLE admin(
   id_admin INT AUTO_INCREMENT,
   nom_admin VARCHAR(50) NOT NULL,
   prenom_admin VARCHAR(50),
   password VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_admin)
);

CREATE TABLE type_pret(
   id_type_pret INT AUTO_INCREMENT,
   type VARCHAR(50) NOT NULL,
   PRIMARY KEY(id_type_pret)
);

CREATE TABLE duree_pret(
   id_duree_pret INT AUTO_INCREMENT,
   duree INT NOT NULL,
   id_profil INT NOT NULL,
   PRIMARY KEY(id_duree_pret),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil)
);

CREATE TABLE statut_reservation(
   id_statut INT AUTO_INCREMENT,
   nom_statut VARCHAR(50) NOT NULL, -- En attente / Expirée / Honorée / Annulée
   PRIMARY KEY(id_statut)
);

CREATE TABLE langue(
   id_langue INT AUTO_INCREMENT,
   langue VARCHAR(50) NOT NULL
);

CREATE TABLE livre(
   id_livre INT AUTO_INCREMENT,
   titre VARCHAR(50) NOT NULL,
   isbn VARCHAR(255) NOT NULL,
   annee_publication INT NOT NULL,
   synopsis TEXT,
   nb_page INT,
   id_editeur INT NOT NULL,
   id_auteur INT NOT NULL,
   id_langue INT NOT NULL,
   PRIMARY KEY(id_livre),
   FOREIGN KEY(id_editeur) REFERENCES editeur(id_editeur),
   FOREIGN KEY(id_auteur) REFERENCES auteur(id_auteur),
   FOREIGN KEY(id_langue) REFERENCES langue(id_langue)
);

CREATE TABLE adherent(
   id_adherent INT AUTO_INCREMENT,
   nom_adherent VARCHAR(50) NOT NULL,
   prenom_adherent VARCHAR(50),
   password VARCHAR(50) NOT NULL,
   id_profil INT NOT NULL,
   PRIMARY KEY(id_adherent),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil)
);

CREATE TABLE abonnement(
   id_abonnement INT AUTO_INCREMENT,
   date_debut DATETIME NOT NULL,
   date_fin DATETIME NOT NULL,
   id_adherent INT NOT NULL,
   PRIMARY KEY(id_abonnement),
   FOREIGN KEY(id_adherent) REFERENCES adherent(id_adherent)
);

CREATE TABLE penalite(
   id_penalite INT AUTO_INCREMENT,
   date_penalite DATETIME NOT NULL,
   id_adherent INT NOT NULL,
   PRIMARY KEY(id_penalite),
   FOREIGN KEY(id_adherent) REFERENCES adherent(id_adherent)
);

CREATE TABLE type_penalite_profil(
   id_type_penalite INT AUTO_INCREMENT,
   duree INT NOT NULL,
   id_profil INT NOT NULL,
   PRIMARY KEY(id_type_penalite),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil)
)

CREATE TABLE exemplaire(
   id_exemplaire INT AUTO_INCREMENT,
   dispo BOOLEAN NOT NULL DEFAULT TRUE,
   id_livre INT NOT NULL,
   PRIMARY KEY(id_exemplaire),
   FOREIGN KEY(id_livre) REFERENCES livre(id_livre)
);

CREATE TABLE pret(
   id_pret INT AUTO_INCREMENT,
   date_debut DATETIME NOT NULL,
   id_admin INT NOT NULL,
   id_type_pret INT NOT NULL,
   id_exemplaire INT NOT NULL,
   id_adherent INT NOT NULL,
   PRIMARY KEY(id_pret),
   FOREIGN KEY(id_admin) REFERENCES admin(id_admin),
   FOREIGN KEY(id_type_pret) REFERENCES type_pret(id_type_pret),
   FOREIGN KEY(id_exemplaire) REFERENCES exemplaire(id_exemplaire),
   FOREIGN KEY(id_adherent) REFERENCES adherent(id_adherent)
);

CREATE TABLE retour_pret(
   id_retour INT AUTO_INCREMENT,
   date_retour DATETIME NOT NULL,
   id_pret INT NOT NULL,
   PRIMARY KEY(id_retour),
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret)
);

CREATE TABLE reservation(
   id_reservation INT AUTO_INCREMENT,
   date_reservation DATETIME NOT NULL,
   date_expiration DATETIME NOT NULL,
   id_statut INT NOT NULL,
   id_exemplaire INT NOT NULL,
   id_adherent INT NOT NULL,
   PRIMARY KEY(id_reservation),
   FOREIGN KEY(id_statut) REFERENCES statut_reservation(id_statut),
   FOREIGN KEY(id_exemplaire) REFERENCES exemplaire(id_exemplaire),
   FOREIGN KEY(id_adherent) REFERENCES adherent(id_adherent)
);

CREATE TABLE duree_reservation(
   id_duree_reservation INT AUTO_INCREMENT,
   duree INT NOT NULL,
   id_profil INT NOT NULL,
   PRIMARY KEY(id_duree_reservation),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil)
);

CREATE TABLE prolongation_pret(
   id_prolongation INT AUTO_INCREMENT,
   date_prolongation DATETIME NOT NULL,
   id_pret INT NOT NULL,
   PRIMARY KEY(id_prolongation),
   FOREIGN KEY(id_pret) REFERENCES pret(id_pret)
)

CREATE TABLE accessibilite_profil_livre(
   id_profil INT NOT NULL,
   id_livre INT NOT NULL,
   PRIMARY KEY(id_profil,id_livre),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil),
   FOREIGN KEY(id_livre) REFERENCES livre(id_livre)
);

CREATE TABLE categorie_livre(
   id_livre INT NOT NULL,
   id_categorie INT NOT NULL,
   PRIMARY KEY(id_livre, id_categorie),
   FOREIGN KEY(id_livre) REFERENCES livre(id_livre),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
);

CREATE TABLE quota_type_pret(
   id_profil INT NOT NULL,
   id_type_pret INT NOT NULL,
   quota INT NOT NULL,
   PRIMARY KEY(id_profil, id_type_pret),
   FOREIGN KEY(id_profil) REFERENCES profil(id_profil),
   FOREIGN KEY(id_type_pret) REFERENCES type_pret(id_type_pret)
);

CREATE TABLE jour_ferie(
   id_jour_ferie INT AUTO_INCREMENT,
   date_ferie DATETIME NOT NULL
);