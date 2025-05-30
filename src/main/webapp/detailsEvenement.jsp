<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 03:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Détails de l'événement</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            border: 1px solid #888;
            padding: 8px;
        }
        th {
            background-color: #ddd;
        }
        .statut-color {
            padding: 3px 8px;
            border-radius: 4px;
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h1>Détails de l'événement : ${evenement.nomEvenement}</h1>

<p><strong>Date et heure :</strong> ${evenement.horodatage}</p>
<p><strong>Durée :</strong> ${evenement.duree.toHours()} heures</p>
<p><strong>Lieu :</strong> ${evenement.lieu}</p>
<p><strong>Description :</strong> ${evenement.description}</p>

<h2>Inscriptions</h2>

<c:choose>
    <c:when test="${not empty inscriptions}">
        <table>
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

</body>
</html>
