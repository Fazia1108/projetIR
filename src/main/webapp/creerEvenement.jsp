<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 03:20
  To change this template use File | Settings | File Templates.
--%>
<form method="post" action="${pageContext.request.contextPath}/creerEvenement">
    <label>Nom: <input type="text" name="nom" required /></label><br/>
    <label>Date et heure: <input type="datetime-local" name="horodatage" required /></label><br/>
    <label>Durée:
        <input type="number" name="heures" min="0" value="1" required /> heures
        <input type="number" name="minutes" min="0" max="59" value="0" required /> minutes
    </label><br/>
    <label>Lieu: <input type="text" name="lieu" required /></label><br/>
    <label>Description: <textarea name="description"></textarea></label><br/>
    <label>Type:
        <select name="type">
            <option value="1">Atelier</option>
            <option value="2">Répétition</option>
            <option value="3">Prestation</option>
        </select>
    </label><br/>
    <button type="submit">Créer</button>
</form>
