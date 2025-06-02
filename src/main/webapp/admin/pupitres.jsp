<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des pupitres - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .button-submit {
            background-color: #28a745;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .button-submit:hover {
            background-color: #218838;
        }
        .actions a,
        .actions form {
            display: inline;
            margin-right: 8px;
        }
        .error-message {
            color: red;
        }
        .success-message {
            color: green;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
    </div>

    <h2>Liste des pupitres</h2>

    <c:if test="${not empty sessionScope.messageSucces}">
        <p class="success-message">${sessionScope.messageSucces}</p>
        <c:remove var="messageSucces" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.messageErreur}">
        <p class="error-message">${sessionScope.messageErreur}</p>
        <c:remove var="messageErreur" scope="session"/>
    </c:if>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pupitre" items="${pupitres}">
            <tr>
                <td>${pupitre.idPupitre}</td>
                <td>${pupitre.nomPupitre}</td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/admin/editerPupitre?id=${pupitre.idPupitre}">Modifier</a>
                    <form action="${pageContext.request.contextPath}/admin/supprimerPupitre" method="post" onsubmit="return confirm('Confirmer la suppression ?');">
                        <input type="hidden" name="idPupitre" value="${pupitre.idPupitre}">
                        <button type="submit" class="button-submit">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p><a href="${pageContext.request.contextPath}/admin/ajouterPupitre" class="button-submit">+ Ajouter un pupitre</a></p>
</div>
</body>
</html>
