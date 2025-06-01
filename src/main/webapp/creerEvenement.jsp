<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un Événement - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/evenements">Liste des événements</a>
    </div>

    <h2>Créer un Événement</h2>

    <form method="post" action="${pageContext.request.contextPath}/creerEvenement">
        <label for="nom">Nom de l'événement :</label>
        <input type="text" id="nom" name="nom" required />

        <label for="horodatage">Date et heure :</label>
        <input type="datetime-local" id="horodatage" name="horodatage" required />

        <label>Durée :</label>
        <div class="form-row">
            <input type="number" name="heures" min="0" value="1" required /> heures
            <input type="number" name="minutes" min="0" max="59" value="0" required /> minutes
        </div>

        <label for="lieu">Lieu :</label>
        <input type="text" id="lieu" name="lieu" required />

        <label for="description">Description :</label>
        <textarea id="description" name="description"></textarea>

        <label for="type">Type :</label>
        <select id="type" name="type">
            <option value="1">Atelier</option>
            <option value="2">Répétition</option>
            <option value="3">Prestation</option>
        </select>

        <button type="submit">Créer l'événement</button>
    </form>
</div>
</body>
</html>