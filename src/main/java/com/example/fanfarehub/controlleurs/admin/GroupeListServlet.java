package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.GroupeDaoImpl;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Groupe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/groupes")
public class GroupeListServlet extends HttpServlet {

    private GroupeDaoImpl groupeDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            groupeDao = new GroupeDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation de GroupeDao", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) session.getAttribute("fanfaronConnecte");

        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        List<Groupe> groupes = groupeDao.findAll();
        request.setAttribute("groupes", groupes);
        request.getRequestDispatcher("/admin/groupes.jsp").forward(request, response);
    }
}
