package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.dao.FanfaronDaoImpl;
import com.example.fanfarehub.dao.GenreDao; // Import
import com.example.fanfarehub.dao.GenreDaoImpl; // Import
import com.example.fanfarehub.dao.ContrainteAlimentaireDao; // Import
import com.example.fanfarehub.dao.ContrainteAlimentaireDaoImpl; // Import
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Genre; // Import
import com.example.fanfarehub.model.ContrainteAlimentaire; // Import
import com.example.fanfarehub.util.PasswordHasher; // Import pour le hachage du mot de passe

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List; // Import
import java.util.Optional;

@WebServlet("/admin/modifierUtilisateur")
public class UtilisateurEditServlet extends HttpServlet {

    private FanfaronDao fanfaronDao;
    private GenreDao genreDao; // Déclaration
    private ContrainteAlimentaireDao contrainteAlimentaireDao; // Déclaration
    private PasswordHasher passwordHasher; // Déclaration

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            fanfaronDao = new FanfaronDaoImpl(connection);
            genreDao = new GenreDaoImpl(connection); // Initialisation
            contrainteAlimentaireDao = new ContrainteAlimentaireDaoImpl(connection); // Initialisation
            passwordHasher = new PasswordHasher(); // Initialisation
        } catch (SQLException e) {
            // Utiliser ServletException pour une meilleure gestion des erreurs d'initialisation
            throw new ServletException("Erreur lors de l'initialisation des DAOs ou de la connexion à la base de données.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérification admin
        HttpSession session = request.getSession(false);
        Fanfaron admin = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        String nomFanfaron = request.getParameter("nom"); // Le paramètre est "nom" de la liste
        if (nomFanfaron == null || nomFanfaron.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/utilisateurs"); // Redirection avec le contexte path
            return;
        }

        Optional<Fanfaron> userOpt = fanfaronDao.findById(nomFanfaron); // Utilise findById
        if (userOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilisateur non trouvé.");
            return;
        }
        Fanfaron utilisateurAModifier = userOpt.get();

        // Charger les listes pour les dropdowns (genre et contraintes)
        List<Genre> genres = genreDao.findAll();
        List<ContrainteAlimentaire> contraintes = contrainteAlimentaireDao.findAll();

        request.setAttribute("utilisateur", utilisateurAModifier);
        request.setAttribute("genres", genres);
        request.setAttribute("contraintes", contraintes);

        request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // S'assurer que les caractères spéciaux sont bien gérés

        // Vérification admin
        HttpSession session = request.getSession(false);
        Fanfaron admin = (Fanfaron) session.getAttribute("fanfaronConnecte");
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux administrateurs.");
            return;
        }

        // Récupération de l'identifiant de l'utilisateur à modifier
        String nomFanfaronOriginal = request.getParameter("nomFanfaronOriginal"); // Nouveau champ hidden pour l'identifiant original
        if (nomFanfaronOriginal == null || nomFanfaronOriginal.trim().isEmpty()) {
            request.getSession().setAttribute("messageErreur", "Identifiant utilisateur manquant pour la modification.");
            response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
            return;
        }

        try {
            Optional<Fanfaron> userOpt = fanfaronDao.findById(nomFanfaronOriginal);
            if (userOpt.isEmpty()) {
                request.getSession().setAttribute("messageErreur", "Utilisateur à modifier introuvable.");
                response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");
                return;
            }
            Fanfaron utilisateurAModifier = userOpt.get();

            // Récupération de tous les paramètres modifiables du formulaire
            String nouveauNomFanfaron = request.getParameter("nomFanfaron"); // Si le nom d'utilisateur peut être changé
            String email = request.getParameter("email");
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            String role = request.getParameter("role");
            String motDePasseSaisi = request.getParameter("motDePasse"); // Nouveau mot de passe (peut être vide)

            String idGenreParam = request.getParameter("id_genre");
            String idContrainteParam = request.getParameter("id_contrainte_alimentaire");

            // --- Validation basique ---
            if (nouveauNomFanfaron == null || nouveauNomFanfaron.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    prenom == null || prenom.trim().isEmpty() ||
                    nom == null || nom.trim().isEmpty() ||
                    role == null || role.trim().isEmpty() ||
                    idGenreParam == null || idGenreParam.trim().isEmpty()) {
                request.getSession().setAttribute("messageErreur", "Tous les champs obligatoires doivent être remplis.");
                request.setAttribute("utilisateur", utilisateurAModifier); // Repasser l'objet original
                request.setAttribute("genres", genreDao.findAll()); // Repasser les listes
                request.setAttribute("contraintes", contrainteAlimentaireDao.findAll()); // Repasser les listes
                request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
                return;
            }

            // --- Mise à jour de l'objet Fanfaron ---
            // Si le nom d'utilisateur est modifiable, assurez-vous qu'il n'entre pas en conflit avec un existant
            if (!nouveauNomFanfaron.equals(utilisateurAModifier.getNomFanfaron())) {
                if (fanfaronDao.findById(nouveauNomFanfaron).isPresent()) {
                    request.getSession().setAttribute("messageErreur", "Le nouveau nom d'utilisateur est déjà pris.");
                    request.setAttribute("utilisateur", utilisateurAModifier);
                    request.setAttribute("genres", genreDao.findAll());
                    request.setAttribute("contraintes", contrainteAlimentaireDao.findAll());
                    request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
                    return;
                }
                utilisateurAModifier.setNomFanfaron(nouveauNomFanfaron); // Mise à jour du nom d'utilisateur
            }

            utilisateurAModifier.setEmail(email);
            utilisateurAModifier.setPrenom(prenom);
            utilisateurAModifier.setNom(nom);
            utilisateurAModifier.setRole(role);

            // Gérer le mot de passe (si modifié)
            if (motDePasseSaisi != null && !motDePasseSaisi.trim().isEmpty()) {
                utilisateurAModifier.setMotDePasse(passwordHasher.hash(motDePasseSaisi));
            }

            // Gérer le genre
            int idGenre = Integer.parseInt(idGenreParam);
            Genre genre = genreDao.findById(idGenre).orElse(null); // Gérer le cas où le genre n'est pas trouvé
            if (genre != null) {
                utilisateurAModifier.setGenre(genre);
            } else {
                request.getSession().setAttribute("messageErreur", "Genre invalide sélectionné.");
                request.setAttribute("utilisateur", utilisateurAModifier);
                request.setAttribute("genres", genreDao.findAll());
                request.setAttribute("contraintes", contrainteAlimentaireDao.findAll());
                request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
                return;
            }

            // Gérer la contrainte alimentaire (peut être nulle)
            ContrainteAlimentaire contrainte = null;
            if (idContrainteParam != null && !idContrainteParam.trim().isEmpty()) {
                int idContrainte = Integer.parseInt(idContrainteParam);
                contrainte = contrainteAlimentaireDao.findById(idContrainte).orElse(null);
            }
            utilisateurAModifier.setContraintesAlimentaires(contrainte); // Définit la contrainte (peut être null)

            // Mise à jour en base de données
            fanfaronDao.update(utilisateurAModifier); // Assurez-vous que votre méthode update gère la modification de tous les champs

            request.getSession().setAttribute("messageSucces", "L'utilisateur " + utilisateurAModifier.getNomFanfaron() + " a été mis à jour avec succès.");
            response.sendRedirect(request.getContextPath() + "/admin/utilisateurs");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("messageErreur", "ID de genre ou de contrainte alimentaire invalide.");
            // Recharger les données pour réafficher le formulaire correctement
            request.setAttribute("genres", genreDao.findAll());
            request.setAttribute("contraintes", contrainteAlimentaireDao.findAll());
            // Tenter de récupérer l'utilisateur avec l'ID original pour le réafficher
            fanfaronDao.findById(nomFanfaronOriginal).ifPresent(u -> request.setAttribute("utilisateur", u));
            request.getRequestDispatcher("/admin/modifierUtilisateur.jsp").forward(request, response);
        }
    }
}