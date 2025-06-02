<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Groupes - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
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
            display: inline-block;
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
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a>
    </div>

    <h2>Gestion des Groupes</h2>

    <form action="${pageContext.request.contextPath}/admin/ajouterGroupe" method="post" style="margin-bottom: 20px;">
        <input type="text" name="nomGroupe" placeholder="Nom du groupe" required>
        <button type="submit">Ajouter un nouveau groupe</button>
    </form>

    <c:if test="${not empty messageSucces}">
        <p style="color: green;">${messageSucces}</p>
    </c:if>
    <c:if test="${not empty messageErreur}">
        <p style="color: red;">${messageErreur}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty groupes}">
            <table class="users-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom du Groupe</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="groupe" items="${groupes}">
                    <tr>
                        <td>${groupe.idGroupe}</td>
                        <td>${groupe.nomGroupe}</td>
                        <td class="user-actions">
                            <form action="${pageContext.request.contextPath}/admin/modifierGroupe" method="post">
                                <input type="hidden" name="idGroupe" value="${groupe.idGroupe}" />
                                <input type="text" name="nomGroupe" value="${groupe.nomGroupe}" required />
                                <button type="submit" class="edit-button">Modifier</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/supprimerGroupe" method="post"
                                  onsubmit="return confirm('Voulez-vous vraiment supprimer ce groupe ?');">
                                <input type="hidden" name="idGroupe" value="${groupe.idGroupe}" />
                                <button type="submit" class="delete-button">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun groupe enregistré.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
