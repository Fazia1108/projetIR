<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Liste des Événements</title>
</head>
<body>
<h2>Liste des Événements</h2>

<c:forEach var="evenement" items="${evenements}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">
        <h3>${evenement.nomEvenement}</h3>
        <p><strong>Lieu :</strong> ${evenement.lieu}</p>
        <p><strong>Date :</strong> ${evenement.horodatage}</p>
        <p><strong>Durée :</strong> ${evenement.heures} h ${evenement.minutes} min</p>
        <p><strong>Description :</strong> ${evenement.description}</p>

        <form action="${pageContext.request.contextPath}/inscriptionEvenementForm" method="get">
            <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />
            <button type="submit">S'inscrire</button>
        </form>

        <!-- Afficher ces boutons uniquement si l'utilisateur est dans le groupe 'prestation' -->
        <c:if test="${estDansPrestation}">
            <form action="${pageContext.request.contextPath}/modifierEvenement" method="get" style="display:inline;">
                <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />
                <button type="submit">Modifier</button>
            </form>
            <form action="${pageContext.request.contextPath}/supprimerEvenement" method="post" style="display:inline;"
                  onsubmit="return confirm('Voulez-vous vraiment supprimer cet événement ?');">
                <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />
                <button type="submit">Supprimer</button>
            </form>
        </c:if>

    </div>
</c:forEach>

</body>
</html>
