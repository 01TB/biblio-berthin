<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Retour de Livres</title>
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
            <h1 class="text-3xl font-bold text-primary text-center mb-6">Retour de Livres</h1>

            <!-- Messages d'erreur/succès -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <span>${error}</span>
                </div>
            </c:if>
            <c:if test="${not empty warning}">
                <div class="alert alert-warning">
                    <span>${warning}</span>
                </div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    <span>${success}</span>
                </div>
            </c:if>

            <form action="/admin/retour/rechercher" method="post" class="space-y-4">
                <div class="form-control">
                    <label for="matriculeAdherent" class="label">
                        <span class="label-text">Matricule Adhérent</span>
                    </label>
                    <input type="number" id="matriculeAdherent" name="matriculeAdherent" 
                           class="input input-bordered bg-lightgray text-darktext" required />
                </div>

                <div class="form-control mt-6">
                    <button type="submit" class="btn bg-primary text-white hover:bg-green-800">
                        Rechercher les prêts
                    </button>
                </div>
            </form>
        </div>

        <!-- Liste des prêts si adhérent trouvé -->
        <c:if test="${not empty adherent}">
            <div class="bg-white rounded-xl shadow-lg p-8 border border-secondary space-y-6">
                <h2 class="text-2xl font-bold text-secondary text-center mb-6">
                    Prêts en cours pour ${adherent.getNomAdherent()} ${adherent.getPrenomAdherent()}
                </h2>
                
                <c:choose>
                    <c:when test="${not empty pretsAdherent}">
                        <div class="overflow-x-auto">
                            <table class="table w-full">
                                <thead>
                                    <tr>
                                        <th>Livre</th>
                                        <th>ISBN</th>
                                        <th>Date Début</th>
                                        <th>Date Fin</th>
                                        <th>Statut</th>
                                        <th>Date de retour</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="pret" items="${pretsAdherent}">
                                        <tr>
                                            <td>${pret.getExemplaire().getLivre().getTitre()}</td>
                                            <td>${pret.getExemplaire().getLivre().getIsbn()}</td>
                                            <td>${pret.getDateDebut().toString().replace('T', ' ')}</td>
                                            <td>${pretService.getDateFinPret(pret).toString().replace('T', ' ')}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${LocalDateTime.now().isBefore(pretService.getDateFinPret(pret) == true)}">
                                                        <span class="badge badge-primary">En cours</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-error">En retard</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <form action="/admin/retour/retourner" method="post" class="inline">
                                                <td>
                                                        <input type="hidden" name="idPret" value="${pret.getIdPret()}">
                                                        <%-- <input type="hidden" name="dateRetour" value="${LocalDateTime.now()}"> --%>
                                                        <label for="dateRetour" class="label">
                                                            <span class="label-text">Date de retour</span>
                                                        </label>
                                                        <input type="datetime-local" name="dateRetour" id="dateRetour"
                                                        class="input input-bordered bg-lightgray text-darktext" required />
                                                </td>
                                                <td>
                                                        <button type="submit" class="btn btn-sm bg-green-600 text-white hover:bg-green-800">
                                                            Retourner
                                                        </button>
                                                </td>
                                            </form>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-center text-gray-500">Aucun prêt en cours pour cet adhérent.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>
    </div>

</body>
</html>