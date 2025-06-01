package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.dao.FanfaronDaoImpl;
import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.model.Fanfaron;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/supprimerUtilisateur") // L'URL qui sera mappée à cette servlet
public class SupprimerUtilisateurServlet extends HttpServlet {

    private FanfaronDao fanfaronDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            fanfaronDao = new FanfaronDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation du DAO Fanfaron pour la suppression", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException { // Ajoute ServletException

        // 1. Vérifier les droits d'administrateur
        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) session.getAttribute("fanfaronConnecte");

        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        // 2. Récupérer le nom d'utilisateur à supprimer
        String nomFanfaronASupprimer = request.getParameter("nom"); // Le nom de l'input hidden dans ta JSP

        if (nomFanfaronASupprimer == null || nomFanfaronASupprimer.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nom d'utilisateur à supprimer manquant.");
            return;
        }

        // Optionnel : Empêcher l'administrateur de se supprimer lui-même
        if (nomFanfaronASupprimer.equals(fanfaronConnecte.getNomFanfaron())) {
            request.getSession().setAttribute("messageErreur", "Vous ne pouvez pas supprimer votre propre compte.");
            response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
            return;
        }

        fanfaronDao.delete(nomFanfaronASupprimer);
        request.getSession().setAttribute("messageSucces", "L'utilisateur " + nomFanfaronASupprimer + " a été supprimé avec succès.");

        response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
    }
}