<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier Utilisateur - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* Styles similaires à ajouterUtilisateur.jsp pour la cohérence */
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
            background-color: #007bff; /* Bleu pour modifier */
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .button-submit:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
            font-weight: bold;
        }
        .success-message {
            color: green;
            margin-bottom: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/admin/utilisateurs">Retour à la liste des utilisateurs</a>
    </div>

    <h2>Modifier l'utilisateur : ${utilisateur.nomFanfaron}</h2>

    <c:if test="${not empty sessionScope.messageErreur}">
        <p class="error-message">${sessionScope.messageErreur}</p>
        <c:remove var="messageErreur" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.messageSucces}">
        <p class="success-message">${sessionScope.messageSucces}</p>
        <c:remove var="messageSucces" scope="session"/>
    </c:if>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/admin/modifierUtilisateur" method="post">
            <%-- Champ caché pour garder l'identifiant original de l'utilisateur --%>
            <input type="hidden" name="nomFanfaronOriginal" value="${utilisateur.nomFanfaron}" />

            <div class="form-group">
                <label for="nomFanfaron">Nom d'utilisateur :</label>
                <input type="text" id="nomFanfaron" name="nomFanfaron" value="${utilisateur.nomFanfaron}" required />
            </div>

            <div class="form-group">
                <label for="email">Email :</label>
                <input type="email" id="email" name="email" value="${utilisateur.email}" required />
            </div>

            <div class="form-group">
                <label for="prenom">Prénom :</label>
                <input type="text" id="prenom" name="prenom" value="${utilisateur.prenom}" required />
            </div>

            <div class="form-group">
                <label for="nom">Nom :</label>
                <input type="text" id="nom" name="nom" value="${utilisateur.nom}" required />
            </div>

            <div class="form-group">
                <label for="id_genre">Genre :</label>
                <select id="id_genre" name="id_genre" required>
                    <option value="">Sélectionner</option>
                    <c:forEach var="genre" items="${genres}">
                        <option value="${genre.id}"
                            ${utilisateur.genre != null && utilisateur.genre.id == genre.id ? 'selected' : ''}>
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
                        <option value="${contrainte.idContrainteAlimentaire}"
                                124: ${utilisateur.contraintesAlimentaires != null && utilisateur.contraintesAlimentaires.idContrainteAlimentaire == contrainte.idContrainteAlimentaire ? 'selected' : ''}>
                                ${contrainte.libelleContrainte}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="role">Rôle :</label>
                <select id="role" name="role" required>
                    <option value="utilisateur" ${utilisateur.role == 'utilisateur' ? 'selected' : ''}>Utilisateur</option>
                    <option value="admin" ${utilisateur.role == 'admin' ? 'selected' : ''}>Administrateur</option>
                </select>
            </div>

            <div class="form-group">
                <label for="motDePasse">Nouveau Mot de passe (laisser vide si inchangé) :</label>
                <input type="password" id="motDePasse" name="motDePasse" placeholder="Saisir un nouveau mot de passe" />
                <small>Si vous entrez un mot de passe ici, il sera haché et remplacera l'ancien.</small>
            </div>

            <button type="submit" class="button-submit">Mettre à jour l'utilisateur</button>
        </form>
    </div>
</div>
</body>
</html>