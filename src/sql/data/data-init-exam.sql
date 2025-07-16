    -- Insertion des auteurs
    INSERT INTO auteur (nom_auteur, prenom_auteur) VALUES
    ('Hugo', 'Victor'),
    ('Camus', 'Albert'),
    ('Rowling', 'J.K.');

    -- Insertion des éditeurs
    INSERT INTO editeur (nom_editeur, localisation) VALUES
    ('Gallimard', 'Paris'),
    ('Bloomsbury', 'Londres'),
    ('Penguin Books', 'New York'),
    ('Hachette', 'Paris'),
    ('Random House', 'Londres');

    -- Insertion des catégories
    INSERT INTO categorie (nom_categorie) VALUES
    ('Littérature classique'),
    ('Philosophie'),
    ('Jeunesse'),
    ('Fantastique');

    -- Insertion des profils
    INSERT INTO profil (nom_profil, quota_reservation, quota_prolongation) VALUES
    ('Etudiant', 1, 3),
    ('Enseignant', 2, 5),
    ('Professionnel', 3, 7);

    -- Insertion des administrateurs
    INSERT INTO admin (matricule, nom_admin, prenom_admin, password) VALUES
    (1001, 'RAMANANKIRAHINA', 'Tsitohaina Berthin', 'admin123'),
    (1002, 'Dupont', 'Jean', 'secure456');

    -- Insertion des types de prêt
    INSERT INTO type_pret (type) VALUES
    ('Sur place'),
    ('Domicile');

    -- Insertion des durées de prêt
    INSERT INTO duree_pret (duree, id_profil) VALUES
    (2, 1), -- 2 jours pour Etudiant
    (3, 2), -- 3 jours pour Enseignant
    (4, 3);  -- 4 jours pour Professionnel

    -- Insertion des statuts de réservation
    INSERT INTO statut_reservation (nom_statut) VALUES
    ('En attente'),
    ('Expirée'),
    ('Honorée'),
    ('Annulée');

    -- Insertion des langues
    INSERT INTO langue (langue) VALUES
    ('Français'),
    ('Anglais'),
    ('Espagnol');

    -- Insertion des livres
    INSERT INTO livre (titre, isbn, annee_publication, synopsis, nb_page, id_editeur, id_auteur, id_langue) VALUES
    ('Les Misérables', '9782070409189', 1862, 'La vie de Jean Valjean et la société française.', 1488, 1, 1, 1),
    ('L''Etranger', '9782070360022', 1844, 'Le livre de Albert Camus.', 672, 1, 2, 1),
    ('Harry Potter à l''école des sorciers', '9782070643026', 1997, 'Les aventures d''un jeune sorcier.', 320, 2, 3, 1);

    -- Insertion des adhérents
    INSERT INTO adherent (matricule, nom_adherent, prenom_adherent, password, id_profil) VALUES
    (2001, 'Amine Bensaïd', 'ETU001', 'pass123', 1),
    (2002, 'Sarah El Khattabi', 'ETU002', 'pass123', 1),
    (2003, 'Youssef Moujahid', 'ETU003', 'pass123', 1),
    (3001, 'Nadia Benali', 'ENS001', 'pass123', 2),
    (3002, 'Karim Haddadi', 'ENS002', 'pass123', 2),
    (3003, 'Salima Touhami', 'ENS003', 'pass123', 2),
    (4001, 'Rachid El Mansouri', 'PROF001', 'pass123', 3),
    (4002, 'Amina Zerouali', 'PROF002', 'pass123', 3);

    -- Insertion des abonnements
    INSERT INTO abonnement (date_debut, date_fin, id_adherent) VALUES
    ('2025-02-01 00:00:00', '2025-07-24 00:00:00', 1),
    ('2025-02-01 00:00:00', '2025-07-01 00:00:00', 2),
    ('2025-04-01 00:00:00', '2025-12-01 00:00:00', 3),
    ('2025-07-01 00:00:00', '2026-07-01 00:00:00', 4),
    ('2025-08-01 00:00:00', '2026-05-01 00:00:00', 5),
    ('2025-07-01 00:00:00', '2026-06-01 00:00:00', 6),
    ('2025-06-01 00:00:00', '2025-12-01 00:00:00', 7),
    ('2024-10-01 00:00:00', '2025-06-01 00:00:00', 8);

    -- Insertion des pénalités
    -- INSERT INTO penalite (date_penalite, id_adherent) VALUES
    -- ('2025-06-15 10:00:00', 1),
    -- ('2025-07-01 14:00:00', 2);

    -- Insertion des types de pénalité par profil
    INSERT INTO type_penalite_profil (duree, id_profil) VALUES
    (10, 1),  -- 10 jours pour Etudiant
    (9, 2),  -- 9 jours pour Professeur
    (8, 3); -- 8 jours pour Externe

    -- Insertion des exemplaires
    INSERT INTO exemplaire (dispo, id_livre) VALUES
    (TRUE, 1),
    (TRUE, 1),
    (TRUE, 1),
    (TRUE, 2),
    (TRUE, 2),
    (TRUE, 3);

    -- Insertion des prêts
    -- INSERT INTO pret (date_debut, id_admin, id_type_pret, id_exemplaire, id_adherent) VALUES
    -- ('2025-07-01 09:00:00', 1, 1, 3, 1),
    -- ('2025-07-02 10:00:00', 2, 2, 4, 2);

    -- Insertion des retours de prêt
    -- INSERT INTO retour_pret (date_retour, id_pret) VALUES
    -- ('2025-07-15 12:00:00', 1);

    -- Insertion des réservations
    -- INSERT INTO reservation (date_reservation, date_expiration, id_statut, id_exemplaire, id_adherent) VALUES
    -- ('2025-07-03 08:00:00', '2025-07-10 08:00:00', 1, 1, 3),
    -- ('2025-07-04 09:00:00', '2025-07-11 09:00:00', 1, 5, 2);

    -- Insertion des durées de réservation
    INSERT INTO duree_reservation (duree, id_profil) VALUES
    (7, 1),  -- 7 jours pour Etudiant
    (10, 2), -- 10 jours pour Professeur
    (5, 3);  -- 5 jours pour Externe

    -- Insertion des prolongations de prêt
    -- INSERT INTO prolongation_pret (date_prolongation, id_pret) VALUES
    -- ('2025-07-10 10:00:00', 2);

    -- Insertion des accessibilités profil-livre
    INSERT INTO accessibilite_profil_livre (id_profil, id_livre) VALUES
    (1, 1), 
    (1, 2), 
    (1, 3), 
    (2, 1), 
    (2, 2), 
    (2, 3), 
    (3, 1), 
    (3, 2), 
    (3, 3); 

    -- Insertion des catégories de livres
    INSERT INTO categorie_livre (id_livre, id_categorie) VALUES
    (1, 1), 
    (2, 2), 
    (3, 3), 
    (3, 4); 

    -- Insertion des quotas de type de prêt
    INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES
    (1, 1, 2), -- Etudiant : 5 prêts sur place
    (1, 2, 2), -- Etudiant : 3 prêt domicile
    (2, 1, 3), -- Enseignant : 7 prêts sur place
    (2, 2, 3), -- Enseignant : 5 prêts domicile
    (3, 1, 4), -- Professionnel : 3 prêts sur place
    (3, 2, 4); -- Professionnel : 2 prêt domicile

    -- Insertion des jours fériés
    INSERT INTO jour_ferie (date_ferie) VALUES
    ('2025-17-13 00:00:00'), 
    ('2025-07-20 00:00:00'), 
    ('2025-07-27 00:00:00'),
    ('2025-08-03 00:00:00'),
    ('2025-08-10 00:00:00'),
    ('2025-08-17 00:00:00'),
    ('2025-07-26 00:00:00'),
    ('2025-07-19 00:00:00');