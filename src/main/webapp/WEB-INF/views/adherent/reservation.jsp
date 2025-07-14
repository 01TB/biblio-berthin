<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Réserver un Livre</title>
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
</head>
<body class="bg-beige min-h-screen py-6 px-4 text-darktext">

    <!-- Section Profil Adhérent -->
    <div class="max-w-5xl mx-auto mb-6 flex justify-between items-center bg-white rounded-lg shadow border border-lightgray p-4">
        <p class="text-darktext font-medium">
            Adhérent : <span class="font-semibold">${adherent.getNomAdherent()} ${adherent.getPrenomAdherent()}</span>
            (Matricule: ${adherent.getMatricule()}) (Profil : ${adherent.getProfil().getNomProfil()})
        </p>
        <a href="/logout" class="btn btn-sm bg-secondary text-white hover:bg-[#5a3a30]">
            Se déconnecter
        </a>
    </div>

    <div class="max-w-5xl mx-auto space-y-8">
        <!-- Formulaire de réservation -->
        <div class="bg-white rounded-xl shadow-lg p-8 border border-primary space-y-6">
            <h1 class="text-3xl font-bold text-primary text-center mb-6">Réserver un Livre</h1>

            <form action="/adherent/reservation" method="post" class="space-y-4">
                <!-- Sélection du livre -->
                <div class="form-control">
                    <label for="livreId" class="label">
                        <span class="label-text">Livre à réserver</span>
                    </label>
                    <select id="livreId" name="livreId" class="select select-bordered bg-lightgray text-darktext" required>
                        <c:forEach var="livre" items="${livres}">
                            <option value="${livre.getIdLivre()}">
                                ${livre.getTitre()} - ${livre.getIsbn()} -
                                <c:forEach var="profil" items="${livre.getProfils()}">
                                    ${profil.getNomProfil()} - 
                                </c:forEach>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Date de réservation -->
                <div class="form-control">
                    <label for="dateReservation" class="label">
                        <span class="label-text">Date de réservation</span>
                    </label>
                    <input type="datetime-local" name="dateReservation" id="dateReservation"
                           class="input input-bordered bg-lightgray text-darktext" required />
                </div>

                <div class="form-control mt-6">
                    <button type="submit" class="btn bg-primary text-white hover:bg-green-800">
                        Valider la réservation
                    </button>
                </div>
            </form>

            <c:if test="${message != null}">
                <div class="alert alert-info mt-4">
                    <span>${message}</span>
                </div>
            </c:if>
        </div>

        <!-- Liste des réservations existantes -->
        <div class="bg-white rounded-xl shadow-lg p-8 border border-secondary space-y-6">
            <h2 class="text-2xl font-bold text-secondary text-center mb-6">Mes Réservations</h2>
            
            <c:choose>
                <c:when test="${not empty reservations}">
                    <div class="overflow-x-auto">
                        <table class="table w-full">
                            <thead>
                                <tr>
                                    <th>Livre</th>
                                    <th>Date Réservation</th>
                                    <th>Date Expiration</th>
                                    <th>Statut</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reservation" items="${reservations}">
                                    <tr>
                                        <td>${reservation.getExemplaire().getLivre().getTitre()}</td>
                                        <td>
                                            ${reservation.getDateReservation().toString().replace('T', ' ')}
                                        </td>
                                        <td>
                                            ${reservation.getDateExpiration().toString().replace('T', ' ')}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${reservation.getStatut().getIdStatut() == 1}">
                                                    <span class="badge badge-primary">En attente</span>
                                                </c:when>
                                                <c:when test="${reservation.getStatut().getIdStatut() == 2}">
                                                    <span class="badge badge-secondary">Expirée</span>
                                                </c:when>
                                                <c:when test="${reservation.getStatut().getIdStatut() == 3}">
                                                    <span class="badge badge-success">Honorée</span>
                                                </c:when>
                                                <c:when test="${reservation.getStatut().getIdStatut() == 4}">
                                                    <span class="badge badge-error">Annulée</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-warning">Inconnu</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${reservation.getStatut().getIdStatut() == 1}">
                                                <form action="/adherent/reservation/annuler" method="post" class="inline">
                                                    <input type="hidden" name="idReservation" value="${reservation.getIdReservation()}">
                                                    <button type="submit" class="btn btn-sm bg-red-500 text-white hover:bg-red-700">
                                                        Annuler
                                                    </button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-center text-gray-500">Vous n'avez aucune réservation en cours.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>