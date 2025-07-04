<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bibliothèque - Prêt de livre</title>
</head>
<body>
    <h1>Prêter un Livre</h1>
    <form action="/preter" method="post">
        <label for="matriculeAdherent">Matricule de l'adhérent : </label>
        <input type="text" id="matriculeAdherent" name="matriculeAdherent" required>
        <%-- <datalist id="adherant-list">
            <c:forEach var="ad" items="${adherants}">
                <option value="${ad.idAdherant}">${ad.nomAdherant} ${ad.prenomAdherant}</option>
            </c:forEach>
        </datalist> --%>
        </br>
        <label for="typePretId">Type de prêt : </label>
        <select name="typePretId" id="typePretId" required>
            <c:ForEach var="typePret" items="${typesPret}">
                <option value="${typesPret.getIdTypePret}">
                    ${typesPret.getType()}
                </option>
            </c:ForEach>
        </select>
        </br>
        <label for="livreId">Sélectionner un livre à preter :</label>
        <select id="livreId" name="livreId" required>
            <c:forEach var="livre" items="${livres}">
                <option value="${livre.getIdLivre()}">
                    ${livre.getTitre()}
                    ${livre.getIsbn()}
                    <%-- <c:if test="${not empty livre.livre}">
                        - Livre: ${livre.livre.titre}
                    </c:if> --%>
                </option>
            </c:forEach>
        </select>
        </br>
        <button type="submit">Valider le pret</button>
    </form>
</body>
</html>