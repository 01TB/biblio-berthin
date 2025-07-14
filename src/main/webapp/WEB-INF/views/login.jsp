<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Login - Bibliothèque</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: '#2E7D32',   // Vert forêt
                        secondary: '#6D4C41', // Brun livre
                        beige: '#F5F5DC',     // Fond beige
                        lightgray: '#E0E0E0', // Fond input
                        darktext: '#212121'   // Texte
                    }
                }
            }
        };
    </script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.4.18/dist/full.css" rel="stylesheet" type="text/css" />
</head>
<body class="min-h-screen bg-beige flex items-center justify-center">

    <div class="w-full max-w-md p-6 bg-white rounded-xl shadow-lg border border-primary">
        
        <!-- Message -->
        <c:if test="${message!=null}">
            <div class="alert alert-warning mb-4">
                <span>${message}</span>
            </div>
        </c:if>

        <!-- Tabs -->
        <div role="tablist" class="tabs tabs-boxed w-full mb-6 bg-lightgray">
            <a role="tab" class="tab tab-active" id="tab-admin" onclick="switchTab('admin')">Admin</a>
            <a role="tab" class="tab" id="tab-adherent" onclick="switchTab('adherent')">Adhérent</a>
        </div>

        <!-- Form Admin -->
        <form id="form-admin" action="/login/admin" method="post" class="space-y-4">
            <div class="form-control">
                <label class="label text-darktext"><span class="label-text">Matricule Admin</span></label>
                <input type="number" name="admin-matricule" class="input input-bordered w-full bg-lightgray text-darktext" required />
            </div>

            <div class="form-control">
                <label class="label text-darktext"><span class="label-text">Mot de passe</span></label>
                <input type="password" name="admin-password" class="input input-bordered w-full bg-lightgray text-darktext" required />
            </div>

            <button class="btn w-full bg-primary text-white hover:bg-green-700">Se connecter</button>
        </form>

        <!-- Form Adherent -->
        <form id="form-adherent" action="/login/adherent" method="post" class="space-y-4 hidden">
            <div class="form-control">
                <label class="label text-darktext"><span class="label-text">Matricule Adhérent</span></label>
                <input type="number" name="adherent-matricule" class="input input-bordered w-full bg-lightgray text-darktext" required />
            </div>

            <div class="form-control">
                <label class="label text-darktext"><span class="label-text">Mot de passe</span></label>
                <input type="password" name="adherent-password" class="input input-bordered w-full bg-lightgray text-darktext" required />
            </div>

            <button class="btn w-full bg-secondary text-white hover:bg-[#5a3a30]">Se connecter</button>
        </form>
    </div>

    <script>
        function switchTab(type) {
            const adminForm = document.getElementById('form-admin');
            const adherentForm = document.getElementById('form-adherent');
            const tabAdmin = document.getElementById('tab-admin');
            const tabAdherent = document.getElementById('tab-adherent');

            if (type === 'admin') {
                adminForm.classList.remove('hidden');
                adherentForm.classList.add('hidden');
                tabAdmin.classList.add('tab-active');
                tabAdherent.classList.remove('tab-active');
            } else {
                adminForm.classList.add('hidden');
                adherentForm.classList.remove('hidden');
                tabAdmin.classList.remove('tab-active');
                tabAdherent.classList.add('tab-active');
            }
        }
    </script>

</body>
</html>
