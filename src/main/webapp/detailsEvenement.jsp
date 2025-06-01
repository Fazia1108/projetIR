<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails de l'événement - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Styles spécifiques pour le tableau d'inscriptions */
        .details-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .details-table th, .details-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        .details-table th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        .statut-color {
            padding: 5px 10px;
            border-radius: 4px;
            color: white;
            font-weight: bold;
            display: inline-block; /* Pour que le padding et le border-radius s'appliquent bien */
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/evenements">Liste des événements</a>
    </div>

    <h1>Détails de l'événement : ${evenement.nomEvenement}</h1>

    <p><strong>Date et heure :</strong> ${evenement.horodatage}</p>
    <p><strong>Durée :</strong> ${evenement.duree.toHours()} heures et ${evenement.duree.toMinutes() % 60} minutes</p>
    <p><strong>Lieu :</strong> ${evenement.lieu}</p>
    <p><strong>Description :</strong> ${evenement.description}</p>

    <h2>Inscriptions</h2>

    <c:choose>
        <c:when test="${not empty inscriptions}">
            <table class="details-table">
                <thead>
                <tr>
                    <th>Fanfaron</th>
                    <th>Instrument</th>
                    <th>Statut</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="inscription" items="${inscriptions}">
                    <tr>
                        <td>${inscription.nomFanfaron}</td>
                        <td>${inscription.nomInstrument}</td>
                        <td>
                                    <span class="statut-color" style="background-color: ${inscription.couleur};">
                                            ${inscription.libelleStatut}
                                    </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>Aucune inscription pour cet événement.</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>