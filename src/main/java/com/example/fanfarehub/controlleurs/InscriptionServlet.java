package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.*;
import com.example.fanfarehub.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import com.example.fanfarehub.util.PasswordHasher;
import java.time.LocalDateTime;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    private Connection connection;
    private FanfaronDaoImpl fanfaronDao;
    private GenreDaoImpl genreDao;
    private ContrainteAlimentaireDaoImpl contrainteDao;
    private PasswordHasher passwordHasher;

    @Override
    public void init() throws ServletException {
        try {
            this.connection = DbConnectionManager.getInstance().getConnection();
            this.fanfaronDao = new FanfaronDaoImpl(connection);
            this.genreDao = new GenreDaoImpl(connection);
            this.contrainteDao = new ContrainteAlimentaireDaoImpl(connection);
            this.passwordHasher = new PasswordHasher();
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation de la connexion JDBC", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Genre> genres = genreDao.findAll();
            List<ContrainteAlimentaire> contraintes = contrainteDao.findAll();

            request.setAttribute("genres", genres);
            request.setAttribute("contraintes", contraintes);

            request.getRequestDispatcher("/inscription.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement du formulaire", e);
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Récupération des paramètres du formulaire
            String nomFanfaron = request.getParameter("nomFanfaron");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            int idGenre = Integer.parseInt(request.getParameter("id_genre"));
            int idContrainte = Integer.parseInt(request.getParameter("id_contrainte_alimentaire"));

            String hashedPassword = passwordHasher.hash(motDePasse);

            // Chargement des entités liées
            Genre genre = genreDao.findById(idGenre).orElse(null);
            ContrainteAlimentaire contrainte = contrainteDao.findById(idContrainte).orElse(null);

            // Création du fanfaron
            Fanfaron fanfaron = new Fanfaron(nomFanfaron, email, hashedPassword, prenom, nom, genre, contrainte);

            // Insertion dans la BDD
            fanfaronDao.create(fanfaron);

            // Redirection vers page de confirmation
            response.sendRedirect("confirmation.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("erreur.jsp"); // à créer pour gérer les erreurs
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
