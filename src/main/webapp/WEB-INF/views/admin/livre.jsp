<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Disponibilité des Exemplaires</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .disponible { color: green; font-weight: bold; }
        .indisponible { color: red; font-weight: bold; }
        .card { margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Disponibilité des Exemplaires</h1>
        
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Recherche par ID de livre</h5>
                <div class="mb-3">
                    <label for="livreId" class="form-label">ID du Livre:</label>
                    <input type="number" class="form-control" id="livreId" placeholder="Entrez l'ID du livre">
                </div>
                <button class="btn btn-primary" onclick="chargerExemplaires()">Rechercher</button>
            </div>
        </div>
        
        <div id="resultats" style="display: none;">
            <div class="card mt-4">
                <div class="card-header">
                    <h2>Informations du Livre</h2>
                </div>
                <div class="card-body">
                    <h3 id="livreTitre" class="card-title"></h3>
                    <p id="livreAuteur" class="card-text"></p>
                    <p id="livreIsbn" class="card-text"></p>
                </div>
            </div>
            
            <div class="card mt-4">
                <div class="card-header">
                    <h2>Liste des Exemplaires</h2>
                </div>
                <div class="card-body">
                    <div id="exemplairesList" class="list-group"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        async function chargerExemplaires() {
            const livreId = document.getElementById('livreId').value;
            if (!livreId) {
                alert('Veuillez entrer un ID de livre');
                return;
            }

            try {
                const response = await fetch('/api/livres/' + livreId + '/exemplaires-disponibilite');
                if (!response.ok) {
                    throw new Error('Livre non trouvé');
                }
                const data = await response.json();
                
                // Afficher la section résultats
                document.getElementById('resultats').style.display = 'block';
                
                // Afficher les informations du livre
                document.getElementById('livreTitre').textContent = data.livre.titre;
                document.getElementById('livreAuteur').textContent = 'Auteur: ' + data.livre.auteur;
                document.getElementById('livreIsbn').textContent = 'ISBN: ' + data.livre.isbn;
                
                // Afficher la liste des exemplaires
                const exemplairesList = document.getElementById('exemplairesList');
                exemplairesList.innerHTML = '';
                
                data.exemplaires.forEach(function(exemplaire) {
                    const exemplaireElement = document.createElement('div');
                    exemplaireElement.className = 'list-group-item';
                    
                    const statusClass = exemplaire.disponible ? 'disponible' : 'indisponible';
                    const statusText = exemplaire.disponible ? 'Disponible' : 'Indisponible';
                    
                    const content = document.createElement('div');
                    content.className = 'd-flex w-100 justify-content-between';
                    
                    const title = document.createElement('h5');
                    title.className = 'mb-1';
                    title.textContent = 'Exemplaire #' + exemplaire.id;
                    
                    const statusSpan = document.createElement('span');
                    statusSpan.className = statusClass;
                    statusSpan.textContent = statusText;
                    
                    content.appendChild(title);
                    content.appendChild(statusSpan);
                    exemplaireElement.appendChild(content);
                    
                    exemplairesList.appendChild(exemplaireElement);
                });
                
            } catch (error) {
                alert('Erreur: ' + error.message);
                console.error(error);
            }
        }
    </script>
</body>
</html>