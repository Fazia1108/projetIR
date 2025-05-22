<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.*, jakarta.servlet.*" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion - FanfareHub</title>
</head>
<body>
<h2>Connexion</h2>
<% String error = (String) request.getAttribute("erreur"); %>
<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<form action="connexion" method="post">
    Nom de fanfaron : <input type="text" name="nomFanfaron" required /><br/>
    Mot de passe : <input type="password" name="motDePasse" required /><br/>
    <input type="submit" value="Se connecter" />
</form>
</body>
</html>