<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 30/05/2025
  Time: 03:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Inscriptions à l’événement</h2>

<c:forEach var="inscription" items="${inscriptions}">
    <p>
        <strong>${inscription.instrument}</strong> -
        <span style="color: ${inscription.couleur}">${inscription.statut}</span> :
            ${inscription.nomFanfaron}
    </p>
</c:forEach>

</body>
</html>
