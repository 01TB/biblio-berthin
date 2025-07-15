<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.LocalDateTime" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prolongation de prêts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .badge-primary { background-color: #007bff; }
        .badge-secondary { background-color: #6c757d; }
        .badge-danger { background-color: #dc3545; }
        .table-responsive { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">Prolongation de prêts</h2>
        
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <div class="card mb-4">
            <div class="card-header">
                Rechercher un adhérent
            </div>
            <div class="card-body">
                <form action="/admin/prolongation/rechercher" method="post">
                    <div class="row g-3 align-items-center">
                        <div class="col-auto">
                            <label for="matriculeAdherent" class="col-form-label">Matricule adhérent:</label>
                        </div>
                        <div class="col-auto">
                            <input type="number" id="matriculeAdherent" name="matriculeAdherent" class="form-control" required>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Rechercher</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <c:if test="${not empty adherent}">
            <div class="card mb-4">
                <div class="card-header">
                    Informations de l'adhérent
                </div>
                <div class="card-body">
                    <p><strong>Nom:</strong> ${adherent.getNomAdherent()}</p>
                    <p><strong>Prénom:</strong> ${adherent.getPrenomAdherent()}</p>
                    <p><strong>Profil:</strong> ${adherent.getProfil().getNomProfil()}</p>
                </div>
            </div>
            
            <div class="card">
                <div class="card-header">
                    Prêts en cours
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty pretsAdherent}">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID Prêt</th>
                                            <th>Exemplaire</th>
                                            <th>Date début</th>
                                            <th>Date fin</th>
                                            <th>Statut</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${pretsAdherent}" var="pret">
                                            <tr>
                                                <td>${pret.idPret}</td>
                                                <td>${pret.exemplaire.ouvrage.titre} (${pret.exemplaire.idExemplaire})</td>
                                                <td><fmt:formatDate value="${pret.dateDebut}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td><fmt:formatDate value="${pretService.getDateFinPret(pret)}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${LocalDateTime.now().isBefore(pretService.getDateFinPret(pret))}">
                                                            <span class="badge badge-primary">En cours</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-danger">En retard</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="/admin/prolongation/prolonger" method="post">
                                                        <input type="hidden" name="matriculeAdherent" value="${adherent.getMatricule()}">
                                                        <input type="hidden" name="idPret" value="${pret.getIdPret()}">
                                                        <button type="submit" class="btn btn-sm btn-success">Prolonger</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="text-muted">Aucun prêt en cours pour cet adhérent.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>