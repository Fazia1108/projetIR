<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 02:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Inscription aux groupes</title>
</head>
<body>
<h2>Choisissez vos groupes</h2>

<form method="post" action="${pageContext.request.contextPath}/choisirGroupes">

    <c:forEach var="groupe" items="${listeGroupes}">
        <label>
            <input
                    type="checkbox"
                    name="groupes"
                    value="${groupe.idGroupe}"
                    <c:if test="${fn:contains(groupesSelectionnesStr, groupe.idGroupe.toString())}">checked</c:if>
            />
                ${groupe.nomGroupe}
        </label><br/>
    </c:forEach>

    <button type="submit">Valider</button>
</form>

</body>
</html>

