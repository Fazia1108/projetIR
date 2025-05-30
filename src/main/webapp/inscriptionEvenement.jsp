<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Inscription à l'événement</title>
</head>
<body>
<h2>Inscription à : ${evenement.nomEvenement}</h2>

<c:if test="${empty fanfaronConnecte}">
    <p>Vous devez être connecté pour vous inscrire.</p>
</c:if>

<c:if test="${not empty fanfaronConnecte}">
    <form method="post" action="${pageContext.request.contextPath}/inscriptionEvenement">
        <input type="hidden" name="idEvenement" value="${evenement.idEvenement}" />

        <label>Pupitre :
            <select name="pupitre" required>
                <c:forEach var="pupitre" items="${listePupitres}">
                    <option value="${pupitre.idPupitre}">${pupitre.nomPupitre}</option>
                </c:forEach>
            </select>
        </label><br/>

        <label>Statut :
            <select name="statut" required>
                <c:forEach var="statut" items="${listeStatuts}">
                    <option value="${statut.idStatut}">${statut.libelleStatut}</option>
                </c:forEach>
            </select>
        </label><br/>

        <button type="submit">S'inscrire</button>
    </form>
</c:if>

</body>
</html>
