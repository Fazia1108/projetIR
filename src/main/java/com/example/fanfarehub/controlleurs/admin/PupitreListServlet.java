package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.PupitreDaoImpl;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Pupitre;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/pupitres")
public class PupitreListServlet extends HttpServlet {

    private PupitreDaoImpl pupitreDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            pupitreDao = new PupitreDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation de la DAO des pupitres", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vérifie si l'utilisateur est un administrateur
        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) session.getAttribute("fanfaronConnecte");

        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        List<Pupitre> pupitres = pupitreDao.findAll();
        request.setAttribute("pupitres", pupitres);
        request.getRequestDispatcher("/admin/pupitres.jsp").forward(request, response);
    }
}
