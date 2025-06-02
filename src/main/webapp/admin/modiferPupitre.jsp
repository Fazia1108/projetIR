<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un pupitre - FanfareHub</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .form-container {
            max-width: 500px;
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
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .button-submit {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-submit:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="main-nav">
        <a href="${pageContext.request.contextPath}/accueil.jsp">Accueil</a> |
        <a href="${pageContext.request.contextPath}/admin/pupitres">Retour à la liste des pupitres</a>
    </div>

    <h2>Modifier le pupitre</h2>

    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/admin/editerPupitre">
            <input type="hidden" name="idPupitre" value="${pupitre.idPupitre}">
            <div class="form-group">
                <label for="nomPupitre">Nom du pupitre :</label>
                <input type="text" id="nomPupitre" name="nomPupitre" required value="${pupitre.nomPupitre}">
            </div>
            <button type="submit" class="button-submit">Enregistrer</button>
        </form>
    </div>
</div>
</body>
</html>
