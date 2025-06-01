<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Utilisateurs - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Styles spécifiques pour le tableau d'utilisateurs */
        .users-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .users-table th, .users-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        .users-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .user-actions form {
            display: inline-block; /* Pour aligner les boutons sur la même ligne */
            margin-right: 5px;
        }
        .user-actions button {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .user-actions .edit-button {
            background-color: #ffc107;
            color: #333;
        }
        .user-actions .delete-button {
            background-color: #dc3545;
            color: white;
        }
        .admin-label {
            color: #28a745; /* Vert pour Admin */
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a>
    </div>

    <h2>Gestion des Utilisateurs</h2>

    <form action="${pageContext.request.contextPath}/admin/ajouterUtilisateur" method="get" style="margin-bottom: 20px;">
        <button type="submit">Ajouter un nouvel utilisateur</button>
    </form>

    <c:choose>
        <c:when test="${not empty utilisateurs}">
            <table class="users-table">
                <thead>
                <tr>
                    <th>Nom utilisateur</th>
                    <th>Email</th>
                    <th>Nom complet</th>
                    <th>Rôle</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${utilisateurs}">
                    <tr>
                        <td>${user.nomFanfaron}</td>
                        <td>${user.email}</td>
                        <td>${user.prenom} ${user.nom}</td>
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
                        <td class="user-actions">
                            <form action="${pageContext.request.contextPath}/admin/modifierUtilisateur" method="get">
                                <input type="hidden" name="nom" value="${user.nomFanfaron}" />
                                <button type="submit" class="edit-button">Modifier</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/supprimerUtilisateur" method="post"
                                  onsubmit="return confirm('Voulez-vous vraiment supprimer ${user.nomFanfaron} ?');">
                                <input type="hidden" name="nom" value="${user.nomFanfaron}" />
                                <button type="submit" class="delete-button">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun utilisateur enregistré.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>