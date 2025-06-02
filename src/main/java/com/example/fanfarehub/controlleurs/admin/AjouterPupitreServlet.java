package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.PupitreDao;
import com.example.fanfarehub.dao.PupitreDaoImpl;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Pupitre;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/ajouterPupitre")
public class AjouterPupitreServlet extends HttpServlet {

    private PupitreDao pupitreDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            this.pupitreDao = new PupitreDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation du DAO Pupitre", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron admin = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;

        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        // Affiche le formulaire JSP d'ajout
        request.getRequestDispatcher("/admin/ajouterPupitre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomPupitre = request.getParameter("nomPupitre");

        if (nomPupitre == null || nomPupitre.trim().isEmpty()) {
            request.getSession().setAttribute("messageErreur", "Le nom du pupitre est requis.");
            response.sendRedirect(request.getContextPath() + "/admin/ajouterPupitre");
            return;
        }

        Pupitre pupitre = new Pupitre();
        pupitre.setNomPupitre(nomPupitre.trim());

        pupitreDao.insertPupitre(pupitre);

        request.getSession().setAttribute("messageSucces", "Pupitre ajouté avec succès.");
        response.sendRedirect(request.getContextPath() + "/admin/pupitres");
    }
}
