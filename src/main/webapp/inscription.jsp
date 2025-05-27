<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <title>Inscription - FanfareHub</title>
</head>
<body>
<form action="inscription" method="post">
    <table>
        <!-- Nom de fanfaron -->
        <tr>
            <td><label for="nomFanfaron">Nom de fanfaron :</label></td>
            <td><input type="text" id="nomFanfaron" name="nomFanfaron" required minlength="3" maxlength="50"></td>
        </tr>

        <!-- Email -->
        <tr>
            <td><label for="email">Adresse email :</label></td>
            <td><input type="email" id="email" name="email" required></td>
        </tr>

        <!-- Confirmation email -->
        <tr>
            <td><label for="confirmationEmail">Confirmer l'email :</label></td>
            <td><input type="email" id="confirmationEmail" name="confirmationEmail" required></td>
        </tr>

        <!-- Mot de passe -->
        <tr>
            <td><label for="motDePasse">Mot de passe :</label></td>
            <td><input type="password" id="motDePasse" name="motDePasse" required minlength="8"></td>
        </tr>

        <!-- Confirmation mot de passe -->
        <tr>
            <td><label for="confirmationMotDePasse">Confirmer le mot de passe :</label></td>
            <td><input type="password" id="confirmationMotDePasse" name="confirmationMotDePasse" required></td>
        </tr>

        <!-- Prénom -->
        <tr>
            <td><label for="prenom">Prénom :</label></td>
            <td><input type="text" id="prenom" name="prenom" required minlength="2" maxlength="50"></td>
        </tr>

        <!-- Nom -->
        <tr>
            <td><label for="nom">Nom :</label></td>
            <td><input type="text" id="nom" name="nom" required minlength="2" maxlength="50"></td>
        </tr>

        <!-- Genre -->
        <tr>
            <td>
                <select name="id_genre" required>
                    <option value="">-- Sélectionnez un genre --</option>
                    <c:forEach items="${genres}" var="genre">
                        <option value="${genre.id}">${genre.libelle}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <!-- Contrainte alimentaire -->
        <tr>
            <td>
                <select name="id_contrainte_alimentaire">
                    <option value="">-- Aucune contrainte --</option>
                    <c:forEach items="${contraintes}" var="c">
                        <option value="${c.idContrainteAlimentaire}">${c.libelleContrainte}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>

    <br>
    <input type="submit" value="S'inscrire">
</form>
</body>
</html>
