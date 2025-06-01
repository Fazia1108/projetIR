<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choisir vos groupes - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a>
    </div>

    <h2>Choisissez vos groupes</h2>

    <form method="post" action="${pageContext.request.contextPath}/choisirGroupes">

        <div class="checkbox-group">
            <c:forEach var="groupe" items="${listeGroupes}">
                <label>
                    <input
                            type="checkbox"
                            name="groupes"
                            value="${groupe.idGroupe}"
                            <c:if test="${fn:contains(groupesSelectionnesStr, groupe.idGroupe.toString())}">checked</c:if>
                    />
                        ${groupe.nomGroupe}
                </label>
            </c:forEach>
        </div>

        <button type="submit">Valider</button>
    </form>
</div>
</body>
</html>