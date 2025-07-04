<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page de Connexion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f2f5;
        }
        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        .tabs {
            display: flex;
            margin-bottom: 20px;
        }
        .tab {
            flex: 1;
            padding: 10px;
            text-align: center;
            cursor: pointer;
            background-color: #e0e0e0;
            border-radius: 4px 4px 0 0;
        }
        .tab.active {
            background-color: #007bff;
            color: white;
        }
        .form-container {
            display: none;
        }
        .form-container.active {
            display: block;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="tabs">
            <div class="tab active" onclick="showForm('admin')">Admin</div>
            <div class="tab" onclick="showForm('adherent')">Adhérent</div>
        </div>
        <div id="admin-form" class="form-container active">
            <form action="/login/admin" method="POST">
                <div class="form-group">
                    <label for="admin-matricule">Matricule</label>
                    <input type="text" id="admin-matricule" name="matricule" required>
                </div>
                <div class="form-group">
                    <label for="admin-password">Mot de passe</label>
                    <input type="password" id="admin-password" name="password" required>
                </div>
                <button type="submit">Se connecter</button>
            </form>
        </div>
        <div id="adherent-form" class="form-container">
            <form action="/login/adherent" method="POST">
                <div class="form-group">
                    <label for="adherent-matricule">Matricule</label>
                    <input type="text" id="adherent-matricule" name="matricule" required>
                </div>
                <div class="form-group">
                    <label for="adherent-password">Mot de passe</label>
                    <input type="password" id="adherent-password" name="password" required>
                </div>
                <button type="submit">Se connecter</button>
            </form>
        </div>
    </div>

    <script>
        function showForm(type) {
            // Gérer les onglets
            document.querySelectorAll('.tab').forEach(tab => {
                tab.classList.remove('active');
            });
            document.querySelectorAll('.form-container').forEach(form => {
                form.classList.remove('active');
            });

            // Activer l'onglet et le formulaire correspondants
            document.querySelector(`.tab[onclick="showForm('${type}')"]`).classList.add('active');
            document.getElementById(`${type}-form`).classList.add('active');
        }
    </script>
</body>
</html>