<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/connexion">Se connecter</a>
    </div>

    <h2>Inscription</h2>

    <form action="inscription" method="post">
        <label for="nomFanfaron">Nom de fanfaron :</label>
        <input type="text" id="nomFanfaron" name="nomFanfaron" required minlength="3" maxlength="50">

        <label for="email">Adresse email :</label>
        <input type="email" id="email" name="email" required>

        <label for="confirmationEmail">Confirmer l'email :</label>
        <input type="email" id="confirmationEmail" name="confirmationEmail" required>

        <label for="motDePasse">Mot de passe :</label>
        <input type="password" id="motDePasse" name="motDePasse" required minlength="8">

        <label for="confirmationMotDePasse">Confirmer le mot de passe :</label>
        <input type="password" id="confirmationMotDePasse" name="confirmationMotDePasse" required>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" required minlength="2" maxlength="50">

        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required minlength="2" maxlength="50">

        <label for="id_genre">Genre :</label>
        <select id="id_genre" name="id_genre" required>
            <option value="">-- Sélectionnez un genre --</option>
            <c:forEach items="${genres}" var="genre">
                <option value="${genre.id}">${genre.libelle}</option>
            </c:forEach>
        </select>

        <label for="id_contrainte_alimentaire">Contrainte alimentaire :</label>
        <select id="id_contrainte_alimentaire" name="id_contrainte_alimentaire">
            <option value="">-- Aucune contrainte --</option>
            <c:forEach items="${contraintes}" var="c">
                <option value="${c.idContrainteAlimentaire}">${c.libelleContrainte}</option>
            </c:forEach>
        </select>

        <input type="submit" value="S'inscrire">
    </form>
</div>
</body>
</html>