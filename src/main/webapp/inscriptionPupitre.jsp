<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choisir vos pupitres - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a>
    </div>

    <h2>Sélectionner vos pupitres</h2>

    <form method="post" action="${pageContext.request.contextPath}/choisirPupitres">

        <div class="checkbox-group">
            <c:forEach var="pupitre" items="${listePupitres}">
                <label>
                    <input
                            type="checkbox"
                            name="pupitres"
                            value="${pupitre.idPupitre}"
                            <c:if test="${fn:contains(pupitresSelectionnesStr, pupitre.idPupitre.toString())}">checked</c:if>
                    />
                        ${pupitre.nomPupitre}
                </label>
            </c:forEach>
        </div>

        <button type="submit">Valider</button>
    </form>
</div>
</body>
</html>