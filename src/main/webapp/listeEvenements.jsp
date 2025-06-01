<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Événements - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .event-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .event-table th, .event-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        .event-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .event-actions form {
            display: inline-block;
            margin-right: 5px;
        }
        .event-actions button {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .event-actions .edit-button {
            background-color: #ffc107;
            color: #333;
        }
        .event-actions .delete-button {
            background-color: #dc3545;
            color: white;
        }
        .create-event-button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a>
    </div>

    <h2>Événements à venir</h2>

    <c:if test="${estDansPrestation}">
        <form action="${pageContext.request.contextPath}/creerEvenement" method="get">
            <button type="submit" class="create-event-button">Créer un nouvel événement</button>
        </form>
    </c:if>

    <c:choose>
        <c:when test="${not empty evenements}">
            <table class="event-table">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Date/Heure</th>
                    <th>Durée</th>
                    <th>Lieu</th>
                    <th>Description</th>
                    <th>Type</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="event" items="${evenements}">
                    <tr>
                        <td>${event.nomEvenement}</td>
                        <td>${event.horodatage}</td>
                        <td>${event.duree.toHours()}h ${event.duree.toMinutes() % 60}min</td>
                        <td>${event.lieu}</td>
                        <td>${event.description}</td>
                        <td>${event.nomTypeEvenement} <%-- Assumes your Evenement model has getNomTypeEvenement() or you fetch it --%></td>
                        <td class="event-actions">
                            <a href="${pageContext.request.contextPath}/inscriptionEvenementForm?idEvenement=${event.idEvenement}">S'inscrire</a>
                            <a href="${pageContext.request.contextPath}/detailsEvenement?id=${event.idEvenement}">Détails</a>
                            <c:if test="${estDansPrestation}">
                                <form action="${pageContext.request.contextPath}/modifierEvenement" method="get">
                                    <input type="hidden" name="idEvenement" value="${event.idEvenement}" />
                                    <button type="submit" class="edit-button">Modifier</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/supprimerEvenement" method="post"
                                      onsubmit="return confirm('Voulez-vous vraiment supprimer l\'événement "${event.nomEvenement}" ?');">
                                <input type="hidden" name="idEvenement" value="${event.idEvenement}" />
                                <button type="submit" class="delete-button">Supprimer</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucun événement n'est prévu pour le moment.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>