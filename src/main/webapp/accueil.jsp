<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Bienvenue sur FanfareHub !</h1>

    <c:choose>
        <c:when test="${not empty sessionScope.fanfaronConnecte}">
            <p>Bonjour, **${sessionScope.fanfaronConnecte.nomFanfaron}** !</p>
            <div class="main-nav">
                <a href="${pageContext.request.contextPath}/deconnexion">Se déconnecter</a>
            </div>
            <h3>Fonctionnalités Connecté :</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/evenements">Voir les événements</a></li>
                <li><a href="${pageContext.request.contextPath}/choisirPupitres">Choisir mes pupitres</a></li>
                <li><a href="${pageContext.request.contextPath}/choisirGroupes">Choisir mes groupes</a></li>
                    <%-- Tu peux ajouter d'autres liens protégés ici --%>
            </ul>
        </c:when>
        <c:otherwise>
            <p>Vous n'êtes pas connecté.</p>
            <div class="main-nav">
                <a href="${pageContext.request.contextPath}/connexion">Se connecter</a> |
                <a href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>