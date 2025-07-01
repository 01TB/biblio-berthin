-- Insertion dans la table auteur
INSERT INTO auteur (nom_auteur, prenom_auteur) VALUES
('Hugo', 'Victor'),
('Rowling', 'J.K.'),
('Tolkien', 'J.R.R.'),
('Austen', 'Jane');

-- Insertion dans la table editeur
INSERT INTO editeur (nom_editeur, localisation) VALUES
('Gallimard', 'Paris'),
('Bloomsbury', 'Londres'),
('Penguin Books', 'New York'),
('Hachette', 'Paris');

-- Insertion dans la table categorie
INSERT INTO categorie (nom_categorie) VALUES
('Roman'),
('Fantasy'),
('Classique'),
('Science-Fiction');

-- Insertion dans la table profil
INSERT INTO profil (nom_profil, quota_reservation, quota_prolongation) VALUES
('Etudiant', 5, 2),
('Professeur', 10, 3),
('Externe', 3, 1);

-- Insertion dans la table admin
INSERT INTO admin (nom_admin, prenom_admin, password) VALUES
('Dupont', 'Jean', 'admin123'),
('Martin', 'Sophie', 'secure456');

-- Insertion dans la table type_pret
INSERT INTO type_pret (type) VALUES
('Court terme'),
('Long terme');

-- Insertion dans la table duree_pret
INSERT INTO duree_pret (duree, id_profil) VALUES
(14, 1), -- Etudiant, 14 jours
(30, 2), -- Professeur, 30 jours
(7, 3);  -- Externe, 7 jours

-- Insertion dans la table statut_reservation
INSERT INTO statut_reservation (nom_statut) VALUES
('En attente'),
('Expirée'),
('Honorée'),
('Annulée');

-- Insertion dans la table langue
INSERT INTO langue (langue) VALUES
('Français'),
('Anglais');

-- Insertion dans la table livre
INSERT INTO livre (titre, isbn, annee_publication, synopsis, nb_page, id_editeur, id_auteur, id_langue) VALUES
('Les Misérables', '978-2070409211', 1862, 'Un roman épique sur la justice et la rédemption.', 1488, 1, 1, 1),
('Harry Potter à l''école des sorciers', '978-0747532699', 1997, 'Les aventures d''un jeune sorcier.', 309, 2, 2, 2),
('Le Seigneur des Anneaux', '978-0141035635', 1954, 'Une quête épique pour détruire l''Anneau.', 1178, 3, 3, 2),
('Orgueil et Préjugés', '978-0141439518', 1813, 'Une histoire d''amour et de classes sociales.', 432, 3, 4, 2);

-- Insertion dans la table accessibilite_profil_livre
INSERT INTO accessibilite_profil_livre (id_profil, id_livre) VALUES
(1, 1), -- Etudiant peut accéder à Les Misérables
(1, 2), -- Etudiant peut accéder à Harry Potter
(2, 3), -- Professeur peut accéder à Le Seigneur des Anneaux
(3, 4); -- Externe peut accéder à Orgueil et Préjugés

-- Insertion dans la table adherent
INSERT INTO adherent (nom_adherent, prenom_adherent, password, id_profil) VALUES
('Durand', 'Marie', 'pass123', 1),
('Lefèvre', 'Paul', 'pass456', 2),
('Moreau', 'Lucie', 'pass789', 3);

-- Insertion dans la table abonnement
INSERT INTO abonnement (date_debut, date_fin, id_adherent) VALUES
('2025-01-01 00:00:00', '2025-12-31 23:59:59', 1),
('2025-02-01 00:00:00', '2025-08-01 23:59:59', 2),
('2025-03-01 00:00:00', '2026-03-01 23:59:59', 3);

-- Insertion dans la table penalite
INSERT INTO penalite (date_penalite, id_adherent) VALUES
('2025-06-01 10:00:00', 1),
('2025-07-15 14:30:00', 2);

-- Insertion dans la table type_penalite_profil
INSERT INTO type_penalite_profil (duree, id_profil) VALUES
(7, 1),  -- Pénalité de 7 jours pour Etudiant
(14, 2), -- Pénalité de 14 jours pour Professeur
(3, 3);  -- Pénalité de 3 jours pour Externe

-- Insertion dans la table exemplaire
INSERT INTO exemplaire (dispo, id_livre) VALUES
(TRUE, 1),
(FALSE, 1),
(TRUE, 2),
(TRUE, 3),
(TRUE, 4);

-- Insertion dans la table pret
INSERT INTO pret (date_debut, id_admin, id_type_pret, id_exemplaire, id_adherent) VALUES
('2025-07-01 09:00:00', 1, 1, 2, 1),
('2025-07-02 10:00:00', 2, 2, 3, 2);

-- Insertion dans la table retour_pret
INSERT INTO retour_pret (date_retour, id_pret) VALUES
('2025-07-15 12:00:00', 1);

-- Insertion dans la table reservation
INSERT INTO reservation (date_reservation, date_expiration, id_statut, id_exemplaire, id_adherent) VALUES
('2025-07-01 08:00:00', '2025-07-08 23:59:59', 1, 3, 1),
('2025-07-02 09:00:00', '2025-07-09 23:59:59', 2, 4, 2);

-- Insertion dans la table duree_reservation
INSERT INTO duree_reservation (duree, id_profil) VALUES
(7, 1),  -- Réservation de 7 jours pour Etudiant
(14, 2), -- Réservation de 14 jours pour Professeur
(5, 3);  -- Réservation de 5 jours pour Externe

-- Insertion dans la table prolongation_pret
INSERT INTO prolongation_pret (date_prolongation, id_pret) VALUES
('2025-07-10 14:00:00', 1);

-- Insertion dans la table categorie_livre
INSERT INTO categorie_livre (id_livre, id_categorie) VALUES
(1, 3), -- Les Misérables -> Classique
(2, 2), -- Harry Potter -> Fantasy
(3, 2), -- Le Seigneur des Anneaux -> Fantasy
(4, 1); -- Orgueil et Préjugés -> Roman

-- Insertion dans la table quota_type_pret
INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES
(1, 1, 3), -- Etudiant, Court terme, 3 prêts
(2, 2, 5), -- Professeur, Long terme, 5 prêts
(3, 1, 2); -- Externe, Court terme, 2 prêts

-- Insertion dans la table jour_ferie
INSERT INTO jour_ferie (date_ferie) VALUES
('2025-12-25 00:00:00'),
('2025-01-01 00:00:00');