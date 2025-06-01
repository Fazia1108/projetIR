package com.example.fanfarehub.util;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter({
        "/creerEvenement/*",
        "/inscriptionEvenement/*",
        "/inscriptionEvenementForm/*", // N'oublie pas le formulaire d'inscription aux événements
        "/choisirGroupes/*",           // Servlet qui gère inscriptionGroupe (action du formulaire)
        "/choisirPupitres/*",          // Servlet qui gère inscriptionPupitre (action du formulaire)
        "/listeEvenements/*",
        "/modifierEvenement/*",
        "/supprimerEvenement/*"        // N'oublie pas la suppression si elle n'est pas déjà couverte
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthFilter initialisé.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // false pour ne pas créer de session si elle n'existe pas

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("AuthFilter intercepte la requête pour : " + path);


        // Vérifie si l'utilisateur est connecté (attribut "fanfaronConnecte" dans la session)
        boolean estConnecte = (session != null && session.getAttribute("fanfaronConnecte") != null);

        // Si l'utilisateur n'est PAS connecté et qu'il essaie d'accéder à une page protégée
        if (!estConnecte) {
            System.out.println("Accès non autorisé pour : " + path + ". Redirection vers la page de connexion.");
            // Redirige vers la page de connexion
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/connexion.jsp");
        } else {
            // Si l'utilisateur est connecté, laisse la requête continuer son chemin
            System.out.println("Accès autorisé pour : " + path);
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage du filtre, si nécessaire.
        System.out.println("AuthFilter détruit.");
    }
}