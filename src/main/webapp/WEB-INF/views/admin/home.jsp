<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Admin</title>
</head>
<body>
    <div class="profile">
        <p>
            Admin : ${admin.getNomAdmin()} ${admin.getPrenomAdmin()} 
            <a href="/logout">Se déconnecter</a>
        </p>
    </div>
    <h1>Que voulez-vous faire ?</h1>
    <a href="/admin/pret">Prêter un Livre</a>
    <a href="/admin/retour">Retourner un Livre</a>
    <a href="/admin/reservation">Gérer les Réservations</a>
    <a href="/admin/prolongation">Prolonger Prêts</a>
</body>
</html>