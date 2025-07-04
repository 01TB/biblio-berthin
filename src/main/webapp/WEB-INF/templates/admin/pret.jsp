<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Prêter un Livre</h1>
    <form action="/pret/preter" method="post">
        <label for="matriculeAdherent">Matricule de l'adhérant :</label>
        <input id="matriculeAdherent" name="matriculeAdherent" required>
        <br/>
        <label for="typePretId">Type de prêt</label>
        <select name="typePretId" id="typePretId" required>
            <c:forEach var="type" items="${typesPret}">
                <option value="${type.getIdTypePret()}">${type.getType()}</option>
            </c:forEach>
        </select>

        <label for="livreId">Sélectionner le livre que vous voulez emprunter : </label>
        <select id="livreId" name="livreId" required>
            <c:forEach var="livre" items="${livres}">
                <option value="${livre.getIdLivre()}">
                    ${livre.getTitre()} - ${livre.getIsbn()}
                </option>
            </c:forEach>
        </select>
        <br><br>
        <button type="submit">Valider le pret</button>
    </form>

    <c:if test="${message != null}">
        <p>${message}</p>
    </c:if>
</body>
</html>