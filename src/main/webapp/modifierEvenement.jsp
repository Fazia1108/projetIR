<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Modifier un Événement</title>
</head>
<body>

<h2>Modifier l'Événement</h2>

<form method="post" action="${pageContext.request.contextPath}/modifierEvenement">
    <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />

    <label>Nom: <input type="text" name="nom" value="${evenement.nomEvenement}" required /></label><br/>

    <label>Date et heure:
        <input type="datetime-local" name="horodatage"
               value="${evenement.horodatage.toLocalDateTime().toString().replace(' ', 'T')}" required />
    </label><br/>

    <label>Durée:
        <input type="number" name="heures" min="0" value="${evenement.duree.toHours()}" required /> heures
        <input type="number" name="minutes" min="0" max="59" value="${evenement.duree.toMinutes() % 60}" required /> minutes
    </label><br/>

    <label>Lieu: <input type="text" name="lieu" value="${evenement.lieu}" required /></label><br/>

    <label>Description: <textarea name="description">${evenement.description}</textarea></label><br/>

    <label>Type:
        <select name="type">
            <option value="1" ${evenement.idTypeEvenement == 1 ? 'selected' : ''}>Atelier</option>
            <option value="2" ${evenement.idTypeEvenement == 2 ? 'selected' : ''}>Répétition</option>
            <option value="3" ${evenement.idTypeEvenement == 3 ? 'selected' : ''}>Prestation</option>
        </select>
    </label><br/>

    <button type="submit">Mettre à jour</button>
</form>

</body>
</html>


