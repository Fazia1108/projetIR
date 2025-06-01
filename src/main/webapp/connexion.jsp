<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.*, jakarta.servlet.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/inscription">S'inscrire</a>
    </div>

    <h2>Connexion</h2>
    <% String error = (String) request.getAttribute("erreur"); %>
    <% if (error != null) { %>
    <p class="error-message"><%= error %></p>
    <% } %>

    <form action="connexion" method="post">
        <label for="nomFanfaron">Nom de fanfaron :</label>
        <input type="text" id="nomFanfaron" name="nomFanfaron" required />

        <label for="motDePasse">Mot de passe :</label>
        <input type="password" id="motDePasse" name="motDePasse" required />

        <input type="submit" value="Se connecter" />
    </form>
</div>
</body>
</html>