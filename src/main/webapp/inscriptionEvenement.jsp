<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription à l'événement - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/evenements">Liste des événements</a>
    </div>

    <h2>Inscription à : ${evenement.nomEvenement}</h2>

    <c:if test="${empty sessionScope.fanfaronConnecte}">
        <p class="error-message">Vous devez être connecté pour vous inscrire.</p>
    </c:if>

    <c:if test="${not empty sessionScope.fanfaronConnecte}">
        <form method="post" action="${pageContext.request.contextPath}/inscriptionEvenement">
            <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />

            <label for="pupitre">Pupitre :</label>
            <select id="pupitre" name="pupitre" required>
                <c:forEach var="pupitre" items="${listePupitres}">
                    <option value="${pupitre.idPupitre}">${pupitre.nomPupitre}</option>
                </c:forEach>
            </select>

            <label for="statut">Statut :</label>
            <select id="statut" name="statut" required>
                <c:forEach var="statut" items="${listeStatuts}">
                    <option value="${statut.idStatut}">${statut.libelleStatut}</option>
                </c:forEach>
            </select>

            <button type="submit">S'inscrire</button>
        </form>
    </c:if>
</div>
</body>
</html>