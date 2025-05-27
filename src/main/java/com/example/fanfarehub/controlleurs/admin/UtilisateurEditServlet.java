package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.dao.FanfaronDaoImpl;
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
import java.util.Optional;

@WebServlet("/admin/modifierUtilisateur")
public class UtilisateurEditServlet extends HttpServlet {

    private FanfaronDao fanfaronDao;

    @Override
    public void init() throws ServletException {
        Connection connection = null;
        try {
            connection = DbConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        fanfaronDao = new FanfaronDaoImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification admin
        HttpSession session = request.getSession(false);
        Fanfaron admin = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String nom = request.getParameter("nom");
        if (nom == null) {
            response.sendRedirect("utilisateurs"); // Liste utilisateurs
            return;
        }

        Optional<Fanfaron> userOpt = fanfaronDao.findByNomFanfaron(nom);
        if (userOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("utilisateur", userOpt.get());
        request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification admin
        HttpSession session = request.getSession(false);
        Fanfaron admin = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Récupération paramètres
        String nom = request.getParameter("nomFanfaron");
        String motDePasse = request.getParameter("motDePasse");
        String role = request.getParameter("role");

        if (nom == null || motDePasse == null || role == null) {
            request.setAttribute("erreur", "Tous les champs sont obligatoires");
            request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
            return;
        }

        Optional<Fanfaron> userOpt = fanfaronDao.findByNomFanfaron(nom);
        if (userOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Fanfaron user = userOpt.get();
        user.setMotDePasse(motDePasse);
        user.setRole(role);
        fanfaronDao.update(user);
        response.sendRedirect("utilisateurs"); // Redirection vers liste
    }
}
