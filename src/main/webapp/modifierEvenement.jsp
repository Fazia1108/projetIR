<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un Événement - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/evenements">Liste des événements</a>
    </div>

    <h2>Modifier l'Événement : ${evenement.nomEvenement}</h2>

    <form method="post" action="${pageContext.request.contextPath}/modifierEvenement">
        <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />

        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="${evenement.nomEvenement}" required />

        <label for="horodatage">Date et heure :</label>
        <input type="datetime-local" id="horodatage" name="horodatage"
               value="${evenement.horodatage.toLocalDateTime().toString().replace(' ', 'T')}" required />

        <label>Durée :</label>
        <div class="form-row">
            <input type="number" name="heures" min="0" value="${evenement.duree.toHours()}" required /> heures
            <input type="number" name="minutes" min="0" max="59" value="${evenement.duree.toMinutes() % 60}" required /> minutes
        </div>

        <label for="lieu">Lieu :</label>
        <input type="text" id="lieu" name="lieu" value="${evenement.lieu}" required />

        <label for="description">Description :</label>
        <textarea id="description" name="description">${evenement.description}</textarea>

        <label for="type">Type :</label>
        <select id="type" name="type">
            <option value="1" ${evenement.idTypeEvenement == 1 ? 'selected' : ''}>Atelier</option>
            <option value="2" ${evenement.idTypeEvenement == 2 ? 'selected' : ''}>Répétition</option>
            <option value="3" ${evenement.idTypeEvenement == 3 ? 'selected' : ''}>Prestation</option>
        </select>

        <button type="submit">Mettre à jour</button>
    </form>
</div>
</body>
</html>