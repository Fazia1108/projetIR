<%--
  Created by IntelliJ IDEA.
  User: amaur
  Date: 27/05/2025
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Modifier utilisateur</title></head>
<body>
<h2>Modifier utilisateur</h2>

<c:if test="${not empty erreur}">
    <p style="color:red">${erreur}</p>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/admin/modifierUtilisateur">
    <input type="hidden" name="nomFanfaron" value="${utilisateur.nomFanfaron}" />
    <p>Nom: ${utilisateur.nomFanfaron}</p>

    <p>
        <label>Mot de passe:</label>
        <input type="password" name="motDePasse" value="${utilisateur.motDePasse}" required />
    </p>

    <p>
        <label>Rôle:</label>
        <select name="role" required>
            <option value="user" ${utilisateur.role == 'user' ? 'selected' : ''}>Utilisateur</option>
            <option value="admin" ${utilisateur.role == 'admin' ? 'selected' : ''}>Administrateur</option>
        </select>
    </p>

    <button type="submit">Enregistrer</button>
</form>

<p><a href="utilisateurs">Retour à la liste</a></p>
</body>
</html>
