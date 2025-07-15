<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Réservations</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#2E7D32',   // Vert forêt
                        secondary: '#6D4C41', // Brun livre
                        beige: '#F5F5DC',     // Fond général
                        lightgray: '#E0E0E0', // Input
                        darktext: '#212121'   // Texte
                    }
                }
            }
        }
    </script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.4.18/dist/full.css" rel="stylesheet" />
    <style>
        .pret-form {
            display: none;
            background-color: rgba(0,0,0,0.4);
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1000;
        }
        .pret-form-content {
            background-color: white;
            margin: 10% auto;
            padding: 20px;
            border-radius: 8px;
            width: 50%;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body class="bg-beige min-h-screen py-6 px-4 text-darktext">

    <!-- Section Admin -->
    <div class="max-w-5xl mx-auto mb-6 flex justify-between items-center bg-white rounded-lg shadow border border-lightgray p-4">
        <p class="text-darktext font-medium">
            Bibliothécaire : <span class="font-semibold">${admin.getNomAdmin()} ${admin.getPrenomAdmin()}</span>
            (Matricule: ${admin.getMatricule()})
        </p>
        <a href="/logout" class="btn btn-sm bg-secondary text-white hover:bg-[#5a3a30]">
            Se déconnecter
        </a>
    </div>

    <div class="max-w-5xl mx-auto space-y-8">
        <!-- Formulaire de recherche -->
        <div class="bg-white rounded-xl shadow-lg p-8 border border-primary space-y-6">
            <h1 class="text-3xl font-bold text-primary text-center mb-6">Gestion des Réservations</h1>

            <!-- Messages d'erreur/succès -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <span>${error}</span>
                </div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                    <span>${message}</span>
                </div>
            </c:if>
            <c:if test="${not empty pretMessage}">
                <div class="alert alert-info">
                    <span>${pretMessage}</span>
                </div>
            </c:if>

            <form action="/admin/reservation/rechercher" method="post" class="space-y-4">
                <div class="form-control">
                    <label for="matriculeAdherent" class="label">
                        <span class="label-text">Matricule Adhérent</span>
                    </label>
                    <input type="number" id="matriculeAdherent" name="matriculeAdherent" 
                           class="input input-bordered bg-lightgray text-darktext" required />
                </div>

                <div class="form-control mt-6">
                    <button type="submit" class="btn bg-primary text-white hover:bg-green-800">
                        Rechercher les réservations
                    </button>
                </div>
            </form>
        </div>

        <!-- Liste des réservations si adhérent trouvé -->
        <c:if test="${not empty adherent}">
            <div class="bg-white rounded-xl shadow-lg p-8 border border-secondary space-y-6">
                <h2 class="text-2xl font-bold text-secondary text-center mb-6">
                    Réservations pour ${adherent.getNomAdherent()} ${adherent.getPrenomAdherent()}
                </h2>
                
                <c:choose>
                    <c:when test="${not empty reservationsAdherent}">
                        <div class="overflow-x-auto">
                            <table class="table w-full">
                                <thead>
                                    <tr>
                                        <th>Livre</th>
                                        <th>Exemplaire</th>
                                        <th>Date Réservation</th>
                                        <th>Date Expiration</th>
                                        <th>Statut</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="reservation" items="${reservationsAdherent}">
                                        <tr>
                                            <td>${reservation.getExemplaire().getLivre().getTitre()}</td>
                                            <td>${reservation.getExemplaire().getIdExemplaire()}</td>
                                            <td>${reservation.getDateReservation().toString().replace('T', ' ')}</td>
                                            <td>${reservation.getDateExpiration().toString().replace('T', ' ')}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${reservation.getDateReservation().isBefore(LocalDateTime.now())
                                                                    and reservation.getDateExpiration().isAfter(LocalDateTime.now())
                                                                    and reservation.getStatut().getIdStatut() == 1}">
                                                        <span class="badge badge-sm badge-primary">En cours</span>
                                                    </c:when>
                                                    <c:when test="${reservation.getDateExpiration().isBefore(LocalDateTime.now())}">
                                                        <span class="badge badge-sm badge-secondary">Expirée</span>
                                                    </c:when>
                                                    <c:when test="${reservation.getStatut().getIdStatut() == 3}">
                                                        <span class="badge badge-sm badge-success">Honorée</span>
                                                    </c:when>
                                                    <c:when test="${reservation.getStatut().getIdStatut() == 4}">
                                                        <span class="badge badge-sm badge-error">En retard</span>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                            <td class="flex space-x-2">
                                                <!-- Formulaire pour changer le statut -->
                                                <form action="/admin/reservation/reserver" method="post" class="flex items-center space-x-2">
                                                    <input type="hidden" name="idReservation" value="${reservation.getIdReservation()}">
                                                    <select name="idStatut" class="select select-bordered select-sm bg-lightgray text-darktext"
                                                            <c:if test="${reservation.getStatut().getIdStatut() == 2}">disabled</c:if>>
                                                        <c:forEach var="statut" items="${allStatutReservation}">
                                                            <option value="${statut.getIdStatut()}" 
                                                                <c:if test="${statut.getIdStatut() == reservation.getStatut().getIdStatut()}">selected</c:if>>
                                                                ${statut.getNomStatut()}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <button type="submit" class="btn btn-sm bg-blue-500 text-white hover:bg-blue-700"
                                                            <c:if test="${reservation.getStatut().getIdStatut() == 2}">disabled</c:if>>
                                                        Modifier
                                                    </button>
                                                </form>
                                                
                                                <!-- Bouton pour ouvrir le formulaire de prêt -->
                                                <c:if test="${reservation.getStatut().getIdStatut() == 1 
                                                and reservationService.isReservationActive(reservation)}">
                                                    <button onclick="openPretForm(
                                                        ${reservation.getExemplaire().getLivre().getIdLivre()},
                                                        '${reservation.getExemplaire().getLivre().getTitre()}',
                                                        ${adherent.getMatricule()},
                                                        ${reservation.getIdReservation()}
                                                    )" class="btn btn-sm bg-green-600 text-white hover:bg-green-800">
                                                        Transformer en prêt
                                                    </button>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-center text-gray-500">Aucune réservation en cours pour cet adhérent.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

    <!-- Formulaire de prêt (modal) -->
    <div id="pretForm" class="pret-form">
        <div class="pret-form-content">
            <span class="close" onclick="closePretForm()" style="float:right;cursor:pointer;font-size:24px">&times;</span>
            <h3 class="text-xl font-bold mb-4">Transformer la réservation en prêt</h3>
            
            <form action="/admin/pret" method="post" class="space-y-4">
                <input type="hidden" id="pretMatricule" name="matriculeAdherent">
                <input type="hidden" id="pretReservationId" name="reservationId">
                <input type="hidden" id="pretLivreId" name="livreId">
                <%-- <input type="hidden" id="pretExemplaireId" name="exemplaireId"> --%>
                
                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Livre</span>
                    </label>
                    <input type="text" id="pretLivreTitre" class="input input-bordered bg-lightgray text-darktext" readonly>
                </div>
                
                <%-- <div class="form-control">
                    <label class="label">
                        <span class="label-text">Exemplaire</span>
                    </label>
                    <input type="text" id="pretExemplaireDisplay" class="input input-bordered bg-lightgray text-darktext" readonly>
                </div> --%>
                
                <div class="form-control">
                    <label for="pretTypePret" class="label">
                        <span class="label-text">Type de prêt</span>
                    </label>
                    <select id="pretTypePret" name="typePretId" class="select select-bordered bg-lightgray text-darktext" required>
                        <c:forEach var="type" items="${typesPret}">
                            <option value="${type.getIdTypePret()}">${type.getType()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-control">
                    <label for="datePret" class="label">
                        <span class="label-text">Date de prêt</span>
                    </label>
                    <input type="datetime-local" name="datePret" id="datePret" class="input input-bordered bg-lightgray text-darktext" required />
                </div>
                
                <div class="flex justify-end space-x-2 mt-6">
                    <button type="button" onclick="closePretForm()" class="btn btn-sm bg-gray-500 text-white hover:bg-gray-700">
                        Annuler
                    </button>
                    <button type="submit" class="btn btn-sm bg-primary text-white hover:bg-green-800">
                        Valider le prêt
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Gestion du formulaire de prêt
        function openPretForm(livreId, livreTitre, matriculeAdherent, reservationId) {
            document.getElementById('pretLivreId').value = livreId;
            document.getElementById('pretLivreTitre').value = livreTitre;
            document.getElementById('pretMatricule').value = matriculeAdherent;
            document.getElementById('pretReservationId').value = reservationId;
            document.getElementById('pretForm').style.display = 'block';
        }
        
        function closePretForm() {
            document.getElementById('pretForm').style.display = 'none';
        }
        
        // Fermer le modal si on clique en dehors
        window.onclick = function(event) {
            const modal = document.getElementById('pretForm');
            if (event.target == modal) {
                closePretForm();
            }
        }
    </script>

</body>
</html>