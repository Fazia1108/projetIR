package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.dao.FanfaronDaoImpl;
import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.GenreDao;
import com.example.fanfarehub.dao.GenreDaoImpl;
import com.example.fanfarehub.dao.ContrainteAlimentaireDao; // Import this
import com.example.fanfarehub.dao.ContrainteAlimentaireDaoImpl; // Import this
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Genre;
import com.example.fanfarehub.model.ContrainteAlimentaire; // Import this
import com.example.fanfarehub.util.PasswordHasher;

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
import java.util.Optional;

@WebServlet("/admin/ajouterUtilisateur")
public class AjouterUtilisateurServlet extends HttpServlet {

    private Connection connection;
    private FanfaronDao fanfaronDao;
    private GenreDao genreDao;
    private ContrainteAlimentaireDao contrainteDao; // Declare this
    private PasswordHasher passwordHasher;

    @Override
    public void init() throws ServletException {
        try {
            this.connection = DbConnectionManager.getInstance().getConnection();
            this.fanfaronDao = new FanfaronDaoImpl(connection);
            this.genreDao = new GenreDaoImpl(connection);
            this.contrainteDao = new ContrainteAlimentaireDaoImpl(connection); // Initialize this
            this.passwordHasher = new PasswordHasher();
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation de la connexion JDBC pour AjouterUtilisateurServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        List<Genre> genres = genreDao.findAll();
        List<ContrainteAlimentaire> contraintes = contrainteDao.findAll(); // Fetch all constraints

        request.setAttribute("genres", genres);
        request.setAttribute("contraintes", contraintes); // Pass constraints to JSP

        request.getRequestDispatcher("/admin/ajouterUtilisateur.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron fanfaronConnecte = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (fanfaronConnecte == null || !"admin".equals(fanfaronConnecte.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        try {
            String nomFanfaron = request.getParameter("nomFanfaron");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            int idGenre = Integer.parseInt(request.getParameter("id_genre"));
            String role = request.getParameter("role");
            // Retrieve dietary constraint ID, handle empty string for "no constraint"
            String contrainteIdParam = request.getParameter("id_contrainte_alimentaire");
            Integer idContrainte = null; // Use Integer to allow null
            if (contrainteIdParam != null && !contrainteIdParam.trim().isEmpty()) {
                idContrainte = Integer.parseInt(contrainteIdParam);
            }

            // Basic validation
            if (nomFanfaron == null || nomFanfaron.trim().isEmpty() ||
                    motDePasse == null || motDePasse.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    prenom == null || prenom.trim().isEmpty() ||
                    nom == null || nom.trim().isEmpty() ||
                    role == null || role.trim().isEmpty()) {
                request.getSession().setAttribute("messageErreur", "Tous les champs sont obligatoires (sauf contrainte alimentaire).");
                doGet(request, response);
                return;
            }

            Optional<Fanfaron> existingFanfaron = fanfaronDao.findById(nomFanfaron);
            if (existingFanfaron.isPresent()) {
                request.getSession().setAttribute("messageErreur", "Ce nom d'utilisateur existe déjà.");
                doGet(request, response);
                return;
            }

            String hashedPassword = passwordHasher.hash(motDePasse);

            Genre genre = genreDao.findById(idGenre).orElseThrow(() -> new ServletException("Genre introuvable pour l'ID: " + idGenre));
            ContrainteAlimentaire contrainte = null;
            if (idContrainte != null) { // Only fetch if an ID was provided
                contrainte = contrainteDao.findById(idContrainte).orElse(null); // Use .orElse(null) if it's optional
            }

            // Create Fanfaron with the retrieved Genre and ContrainteAlimentaire (which can be null)
            Fanfaron fanfaron = new Fanfaron(nomFanfaron, email, hashedPassword, prenom, nom, genre, contrainte);
            fanfaron.setRole(role);

            fanfaronDao.create(fanfaron);

            request.getSession().setAttribute("messageSucces", "L'utilisateur " + nomFanfaron + " a été ajouté avec succès.");
            response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("messageErreur", "ID de genre ou de contrainte alimentaire invalide.");
            doGet(request, response);
        } catch (ServletException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("messageErreur", "Erreur interne lors de l'ajout de l'utilisateur.");
            doGet(request, response);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion dans AjouterUtilisateurServlet : " + e.getMessage());
        }
    }
}