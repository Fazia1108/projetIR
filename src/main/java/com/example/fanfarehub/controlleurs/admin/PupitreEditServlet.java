package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.PupitreDao;
import com.example.fanfarehub.dao.PupitreDaoImpl;
import com.example.fanfarehub.model.Pupitre;
import com.example.fanfarehub.model.Fanfaron;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/editerPupitre")
public class PupitreEditServlet extends HttpServlet {

    private PupitreDao pupitreDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            this.pupitreDao = new PupitreDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation de PupitreDao", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron admin = (Fanfaron) (session != null ? session.getAttribute("fanfaronConnecte") : null);

        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Pupitre pupitre = pupitreDao.findAll().stream()
                        .filter(p -> p.getIdPupitre() == id)
                        .findFirst()
                        .orElse(null);
                request.setAttribute("pupitre", pupitre);
            } catch (NumberFormatException ignored) {}
        }

        request.getRequestDispatcher("/admin/modiferPupitre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nomPupitre");
        String idParam = request.getParameter("idPupitre");

        if (nom == null || nom.trim().isEmpty()) {
            request.getSession().setAttribute("messageErreur", "Le nom du pupitre est requis.");
            response.sendRedirect(request.getContextPath() + "/admin/modiferPupitre.jsp");
            return;
        }

        Pupitre pupitre = new Pupitre();
        pupitre.setNomPupitre(nom.trim());

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                pupitreDao.updatePupitre(id, pupitre);
                request.getSession().setAttribute("messageSucces", "Pupitre modifié avec succès.");
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("messageErreur", "ID de pupitre invalide.");
            }
        } else {
            pupitreDao.insertPupitre(pupitre);
            request.getSession().setAttribute("messageSucces", "Nouveau pupitre ajouté.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/pupitres");
    }
}
