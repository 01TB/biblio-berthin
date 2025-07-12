<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Prêter un Livre</title>
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
<body class="bg-beige min-h-screen flex items-center justify-center py-10 px-4 text-darktext">

    <div class="w-full max-w-2xl bg-white rounded-xl shadow-lg p-8 border border-primary space-y-6">

        <h1 class="text-3xl font-bold text-primary text-center mb-6">Prêter un Livre</h1>

        <form action="/admin/pret" method="post" class="space-y-4">

            <div class="form-control">
                <label for="matriculeAdherent" class="label">
                    <span class="label-text">Matricule de l'adhérent</span>
                </label>
                <input id="matriculeAdherent" name="matriculeAdherent" type="text"
                       class="input input-bordered bg-lightgray text-darktext" required />
            </div>

            <div class="form-control">
                <label for="typePretId" class="label">
                    <span class="label-text">Type de prêt</span>
                </label>
                <select name="typePretId" id="typePretId" class="select select-bordered bg-lightgray text-darktext" required>
                    <c:forEach var="type" items="${typesPret}">
                        <option value="${type.getIdTypePret()}">${type.getType()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-control">
                <label for="livreId" class="label">
                    <span class="label-text">Livre à emprunter</span>
                </label>
                <select id="livreId" name="livreId" class="select select-bordered bg-lightgray text-darktext" required>
                    <c:forEach var="livre" items="${livres}">
                        <option value="${livre.getIdLivre()}">
                            ${livre.getTitre()} - ${livre.getIsbn()}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-control mt-6">
                <button type="submit" class="btn bg-primary text-white hover:bg-green-800">
                    Valider le prêt
                </button>
            </div>
        </form>

        <c:if test="${message != null}">
            <div class="alert alert-info mt-4">
                <span>${message}</span>
            </div>
        </c:if>

    </div>

</body>
</html>
