<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 27/05/2025
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des utilisateurs</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 10px;
            border: 1px solid #aaa;
            text-align: left;
        }
        th {
            background-color: #f0f0f0;
        }
        .admin-label {
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>Liste des utilisateurs</h2>

<table>
    <thead>
    <tr>
        <th>Nom utilisateur</th>
        <th>Email</th>
        <th>Nom complet</th>
        <th>Genre</th>
        <th>Rôle</th>
        <th>Dernière connexion</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${utilisateurs}">
        <tr>
            <td>${user.nomFanfaron}</td>
            <td>${user.email}</td>
            <td>${user.prenom} ${user.nom}</td>
            <td>${user.genre}</td>
            <td>
                <c:choose>
                    <c:when test="${user.role eq 'admin'}">
                        <span class="admin-label">Administrateur</span>
                    </c:when>
                    <c:otherwise>
                        Utilisateur
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${user.derniereConnexion}</td>
            <td>
                <a href="modifierUtilisateur?nom=${user.nomFanfaron}">Modifier</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

