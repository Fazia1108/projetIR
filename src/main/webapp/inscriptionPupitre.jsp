<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 01:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head><title>Choisir vos pupitres</title></head>
<body>
<h2>Sélectionner vos pupitres</h2>

<form method="post" action="${pageContext.request.contextPath}/choisirPupitres">

    <c:forEach var="pupitre" items="${listePupitres}">
        <label>
            <input
                    type="checkbox"
                    name="pupitres"
                    value="${pupitre.idPupitre}"
                    <c:if test="${fn:contains(pupitresSelectionnesStr, pupitre.idPupitre.toString())}">checked</c:if>
            />
                ${pupitre.nomPupitre}
        </label><br/>
    </c:forEach>

    <button type="submit">Valider</button>
</form>

</body>
</html>
