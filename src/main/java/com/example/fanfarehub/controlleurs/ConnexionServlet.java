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
import com.example.fanfarehub.util.PasswordHasher;

@WebServlet("/connexion")
public class ConnexionServlet extends HttpServlet {

    private FanfaronDaoImpl fanfaronDao;
    private PasswordHasher passwordHasher;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            this.fanfaronDao = new FanfaronDaoImpl(connection);
            this.passwordHasher = new PasswordHasher();
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simplement afficher la page connexion.jsp
        request.getRequestDispatcher("/connexion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomFanfaron = request.getParameter("nomFanfaron");
        String motDePasse = request.getParameter("motDePasse");

        try {
            Optional<Fanfaron> optionalFanfaron = fanfaronDao.findByNomFanfaron(nomFanfaron);

            if (optionalFanfaron.isPresent()) {
                Fanfaron fanfaron = optionalFanfaron.get();
                String hashedPasswordFromDB = fanfaron.getMotDePasse();

                boolean passwordMatches = passwordHasher.checkPassword(motDePasse, hashedPasswordFromDB);

                if (passwordMatches) {
                    HttpSession session = request.getSession();
                    session.setAttribute("fanfaronConnecte", fanfaron);
                    response.sendRedirect("accueil.jsp");
                } else {
                    request.setAttribute("erreur", "Nom ou mot de passe incorrect.");
                    request.getRequestDispatcher("/connexion.jsp").forward(request, response);
                }
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
