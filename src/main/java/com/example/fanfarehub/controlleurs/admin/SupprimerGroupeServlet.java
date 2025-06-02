package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.GroupeDaoImpl;
import com.example.fanfarehub.model.Fanfaron;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/supprimerGroupe")
public class SupprimerGroupeServlet extends HttpServlet {

    private GroupeDaoImpl groupeDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            groupeDao = new GroupeDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation du GroupeDao", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron fanfaron = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;

        if (fanfaron == null || !"admin".equals(fanfaron.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        String idParam = request.getParameter("idGroupe");
        try {
            int id = Integer.parseInt(idParam);
            groupeDao.deleteGroupe(id);
            session.setAttribute("messageSucces", "Le groupe a été supprimé.");
        } catch (Exception e) {
            session.setAttribute("messageErreur", "Erreur lors de la suppression.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/groupes");
    }
}
