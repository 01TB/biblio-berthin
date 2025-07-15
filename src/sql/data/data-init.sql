    -- Insertion des auteurs
    INSERT INTO auteur (nom_auteur, prenom_auteur) VALUES
    ('Dumas', 'Alexandre'),
    ('Rowling', 'J.K.'),
    ('Tolkien', 'J.R.R.'),
    ('Hugo', 'Victor'),
    ('Austen', 'Jane');

    -- Insertion des éditeurs
    INSERT INTO editeur (nom_editeur, localisation) VALUES
    ('Gallimard', 'Paris'),
    ('Bloomsbury', 'Londres'),
    ('Penguin Books', 'New York'),
    ('Hachette', 'Paris'),
    ('Random House', 'Londres');

    -- Insertion des catégories
    INSERT INTO categorie (nom_categorie) VALUES
    ('Roman'),
    ('Fantasy'),
    ('Classique'),
    ('Science-fiction'),
    ('Romance');

    -- Insertion des profils
    INSERT INTO profil (nom_profil, quota_reservation, quota_prolongation) VALUES
    ('Etudiant', 5, 2),
    ('Professeur', 10, 3),
    ('Externe', 3, 1);

    -- Insertion des administrateurs
    INSERT INTO admin (matricule, nom_admin, prenom_admin, password) VALUES
    (1001, 'Martin', 'Claire', 'admin123'),
    (1002, 'Dupont', 'Jean', 'secure456');

    -- Insertion des types de prêt
    INSERT INTO type_pret (type) VALUES
    ('Sur place'),
    ('Domicile');

    -- Insertion des durées de prêt
    INSERT INTO duree_pret (duree, id_profil) VALUES
    (7, 1), -- 14 jours pour Etudiant
    (10, 2), -- 30 jours pour Professeur
    (5, 3);  -- 7 jours pour Externe

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
    ('Les Trois Mousquetaires', '978-2070408504', 1844, 'Aventures de d''Artagnan et ses amis mousquetaires.', 672, 1, 1, 1),
    ('Harry Potter à l''école des sorciers', '978-0747532699', 1997, 'Les aventures d''un jeune sorcier.', 320, 2, 2, 2),
    ('Le Seigneur des Anneaux', '978-0261103252', 1954, 'Une quête épique pour détruire l''Anneau Unique.', 1216, 2, 3, 2),
    ('Les Misérables', '978-2253009689', 1862, 'La vie de Jean Valjean et la société française.', 1488, 4, 4, 1),
    ('Orgueil et Préjugés', '978-0141439518', 1813, 'Une histoire d''amour et de classes sociales.', 432, 3, 5, 2);

    -- Insertion des adhérents
    INSERT INTO adherent (matricule, nom_adherent, prenom_adherent, password, id_profil) VALUES
    (2001, 'Lefevre', 'Sophie', 'pass123', 1),
    (2002, 'Moreau', 'Luc', 'secure789', 2),
    (2003, 'Girard', 'Emma', 'user456', 3);

    -- Insertion des abonnements
    INSERT INTO abonnement (date_debut, date_fin, id_adherent) VALUES
    ('2025-01-01 00:00:00', '2026-01-01 00:00:00', 1),
    ('2025-02-01 00:00:00', '2026-02-01 00:00:00', 2),
    ('2025-03-01 00:00:00', '2026-03-01 00:00:00', 3);

    -- Insertion des pénalités
    INSERT INTO penalite (date_penalite, id_adherent) VALUES
    ('2025-06-15 10:00:00', 1),
    ('2025-07-01 14:00:00', 2);

    -- Insertion des types de pénalité par profil
    INSERT INTO type_penalite_profil (duree, id_profil) VALUES
    (7, 1),  -- 7 jours pour Etudiant
    (3, 2),  -- 3 jours pour Professeur
    (10, 3); -- 10 jours pour Externe

    -- Insertion des exemplaires
    INSERT INTO exemplaire (dispo, id_livre) VALUES
    (TRUE, 1),
    (TRUE, 1),
    (TRUE, 2),
    (TRUE, 3),
    (TRUE, 4),
    (TRUE, 5);

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
    (1, 1), -- Etudiant peut accéder aux Trois Mousquetaires
    (1, 2), -- Etudiant peut accéder à Harry Potter
    (1, 3), -- Etudiant peut accéder au Seigneur des Anneaux
    (2, 1), -- Professeur peut accéder aux Trois Mousquetaires
    (2, 2), -- Professeur peut accéder à Harry Potter
    (2, 3), -- Professeur peut accéder au Seigneur des Anneaux
    (2, 4), -- Professeur peut accéder aux Misérables
    (2, 5), -- Professeur peut à Orgueil et Préjugés
    (3, 5); -- Externe peut accéder à Orgueil et Préjugés

    -- Insertion des catégories de livres
    INSERT INTO categorie_livre (id_livre, id_categorie) VALUES
    (1, 3), -- Les Trois Mousquetaires : Classique
    (2, 2), -- Harry Potter : Fantasy
    (3, 2), -- Le Seigneur des Anneaux : Fantasy
    (4, 3), -- Les Misérables : Classique
    (5, 5); -- Orgueil et Préjugés : Romance

    -- Insertion des quotas de type de prêt
    INSERT INTO quota_type_pret (id_profil, id_type_pret, quota) VALUES
    (1, 1, 5), -- Etudiant : 5 prêts sur place
    (1, 2, 3), -- Etudiant : 3 prêt domicile
    (2, 1, 7), -- Professeur : 7 prêts sur place
    (2, 2, 5), -- Professeur : 5 prêts domicile
    (3, 1, 3), -- Externe : 3 prêts sur place
    (3, 2, 2); -- Externe : 2 prêt domicile

    -- Insertion des jours fériés
    INSERT INTO jour_ferie (date_ferie) VALUES
    ('2025-12-25 00:00:00'), -- Noël
    ('2025-01-01 00:00:00'), -- Jour de l'an
    ('2025-07-14 00:00:00'); -- Fête nationale