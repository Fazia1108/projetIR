package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.PupitreDao;
import com.example.fanfarehub.dao.PupitreDaoImpl;
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

@WebServlet("/admin/supprimerPupitre")
public class SupprimerPupitreServlet extends HttpServlet {

    private PupitreDao pupitreDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            pupitreDao = new PupitreDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation de PupitreDao", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Vérifie si l'utilisateur est un administrateur
        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) (session != null ? session.getAttribute("fanfaronConnecte") : null);

        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        // 2. Récupération de l'ID du pupitre à supprimer
        String idParam = request.getParameter("idPupitre");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID du pupitre manquant.");
            return;
        }

        try {
            int idPupitre = Integer.parseInt(idParam);
            pupitreDao.deletePupitre(idPupitre);
            session.setAttribute("messageSucces", "Le pupitre a été supprimé avec succès.");
        } catch (NumberFormatException e) {
            session.setAttribute("messageErreur", "ID de pupitre invalide.");
        } catch (Exception e) {
            session.setAttribute("messageErreur", "Erreur lors de la suppression du pupitre.");
            e.printStackTrace(); // à remplacer par un logger en prod
        }

        response.sendRedirect(request.getContextPath() + "/admin/pupitres");
    }
}
