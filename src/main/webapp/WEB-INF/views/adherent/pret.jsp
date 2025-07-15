<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Prêts en Cours</title>
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
        <!-- Liste des prêts en cours -->
        <div class="bg-white rounded-xl shadow-lg p-8 border border-primary space-y-6">
            <h1 class="text-3xl font-bold text-primary text-center mb-6">Mes Prêts en Cours</h1>

            <c:choose>
                <c:when test="${not empty pretsAdherent}">
                    <div class="overflow-x-auto">
                        <table class="table w-full">
                            <thead>
                                <tr>
                                    <th>Livre</th>
                                    <th>Date de Début</th>
                                    <th>Date de Fin</th>
                                    <th>Type de Prêt</th>
                                    <th>Statut</th>
                                    <th>Prolongations</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="pret" items="${pretsAdherent}">
                                    <tr>
                                        <td>${pret.getExemplaire().getLivre().getTitre()}</td>
                                        <td>${pret.getDateDebut().toString().replace('T', ' ')}</td>
                                        <td>${pretService.getDateFinPret(pret).toString().replace('T', ' ')}</td>
                                        <td>${pret.getTypePret().getType()}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${LocalDateTime.now().isBefore(pretService.getDateFinPret(pret))}">
                                                    <span class="badge badge-primary">En cours</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-secondary">En retard</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:forEach var="prolongation" items="${prolongations}">
                                                <c:if test="${prolongation.getPret().getIdPret() == pret.getIdPret()}">
                                                    <div class="text-xs">
                                                        Prolongé le: ${prolongation.getDateProlongation().toString().replace('T', ' ')}
                                                    </div>
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-center text-gray-500">Vous n'avez aucun prêt en cours.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</body>
</html>