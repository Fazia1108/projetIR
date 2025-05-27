package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
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

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    private FanfaronDaoImpl fanfaronDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            this.fanfaronDao = new FanfaronDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation DAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomFanfaron = request.getParameter("nomFanfaron");
        String motDePasse = request.getParameter("motDePasse");

        try {
            Optional<Fanfaron> optionalFanfaron = fanfaronDao.findByNomFanfaron(nomFanfaron);

            if (optionalFanfaron.isPresent() && optionalFanfaron.get().getMotDePasse().equals(motDePasse)) {
                HttpSession session = request.getSession();
                session.setAttribute("fanfaronConnecte", optionalFanfaron.get());
                response.sendRedirect("accueil.jsp");
            } else {
                request.setAttribute("erreur", "Nom ou mot de passe incorrect.");
                request.getRequestDispatcher("/connexion.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur serveur.");
            request.getRequestDispatcher("/connexion.jsp").forward(request, response);
        }
    }
}
