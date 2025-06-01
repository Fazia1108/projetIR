<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier Utilisateur - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/admin/utilisateurs">Retour à la liste des utilisateurs</a>
    </div>

    <h2>Modifier l'utilisateur : ${utilisateur.nomFanfaron}</h2>

    <c:if test="${not empty erreur}">
        <p class="error-message">${erreur}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/modifierUtilisateur" method="post">
        <input type="hidden" name="nomFanfaron" value="${utilisateur.nomFanfaron}" />

        <label for="email">Email :</label>
        <input type="email" id="email" name="email" value="${utilisateur.email}" disabled />
        <small>L'email ne peut pas être modifié ici.</small>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="${utilisateur.prenom}" disabled />
        <small>Le prénom ne peut pas être modifié ici.</small>

        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="${utilisateur.nom}" disabled />
        <small>Le nom ne peut pas être modifié ici.</small>

        <label for="role">Rôle :</label>
        <select id="role" name="role" required>
            <option value="user" ${utilisateur.role == 'user' ? 'selected' : ''}>Utilisateur</option>
            <option value="admin" ${utilisateur.role == 'admin' ? 'selected' : ''}>Administrateur</option>
        </select>

        <label for="motDePasse">Nouveau Mot de passe (laisser vide si inchangé) :</label>
        <input type="password" id="motDePasse" name="motDePasse" placeholder="Saisir un nouveau mot de passe" />
        <small>Si vous entrez un mot de passe ici, il sera haché et remplacera l'ancien.</small>

        <button type="submit">Mettre à jour l'utilisateur</button>
    </form>
</div>
</body>
</html>