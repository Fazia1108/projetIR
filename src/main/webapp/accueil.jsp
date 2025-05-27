<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 27/05/2025
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.fanfarehub.model.Fanfaron" %>
<%@ page session="true" %>
<%
    Fanfaron fanfaron = (Fanfaron) session.getAttribute("fanfaronConnecte");
    if (fanfaron == null) {
        response.sendRedirect("connexion.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<h1>Bienvenue, <%= fanfaron.getNomFanfaron() %> !</h1>
<p>Vous êtes connecté.</p>
</body>
</html>
