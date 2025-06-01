<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Utilisateur - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input[type="text"],
        .form-group input[type="password"],
        .form-group input[type="email"],
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .button-submit {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .button-submit:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/admin/utilisateurs">Retour à la gestion des utilisateurs</a>
    </div>

    <h2>Ajouter un nouvel utilisateur</h2>

    <c:if test="${not empty sessionScope.messageErreur}">
        <p class="error-message">${sessionScope.messageErreur}</p>
        <c:remove var="messageErreur" scope="session"/>
    </c:if>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/admin/ajouterUtilisateur" method="post">
            <div class="form-group">
                <label for="nomFanfaron">Nom d'utilisateur :</label>
                <input type="text" id="nomFanfaron" name="nomFanfaron" required value="${param.nomFanfaron}">
            </div>

            <div class="form-group">
                <label for="motDePasse">Mot de passe :</label>
                <input type="password" id="motDePasse" name="motDePasse" required>
            </div>

            <div class="form-group">
                <label for="email">Email :</label>
                <input type="email" id="email" name="email" required value="${param.email}">
            </div>

            <div class="form-group">
                <label for="nom">Nom :</label>
                <input type="text" id="nom" name="nom" required value="${param.nom}">
            </div>

            <div class="form-group">
                <label for="prenom">Prénom :</label>
                <input type="text" id="prenom" name="prenom" required value="${param.prenom}">
            </div>

            <div class="form-group">
                <label for="id_genre">Genre :</label>
                <select id="id_genre" name="id_genre" required>
                    <option value="">Sélectionner</option>
                    <c:forEach var="genre" items="${genres}">
                        <option value="${genre.id}" ${param.id_genre == genre.id ? 'selected' : ''}>
                                ${genre.libelle}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="id_contrainte_alimentaire">Contrainte alimentaire :</label>
                <select id="id_contrainte_alimentaire" name="id_contrainte_alimentaire">
                    <option value="">-- Aucune contrainte --</option>
                    <c:forEach var="contrainte" items="${contraintes}">
                        <option value="${contrainte.idContrainteAlimentaire}" ${param.id_contrainte_alimentaire == contrainte.idContrainteAlimentaire ? 'selected' : ''}>
                                ${contrainte.libelleContrainte}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="role">Rôle :</label>
                <select id="role" name="role" required>
                    <option value="utilisateur" ${param.role == 'utilisateur' ? 'selected' : ''}>Utilisateur</option>
                    <option value="admin" ${param.role == 'admin' ? 'selected' : ''}>Administrateur</option>
                </select>
            </div>

            <button type="submit" class="button-submit">Ajouter l'utilisateur</button>
        </form>
    </div>
</div>
</body>
</html>