package com.example.fanfarehub.controlleurs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deconnexion")
public class DeconnexionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Ne crée pas de session si elle n'existe pas
        if (session != null) {
            session.invalidate(); // Invalide la session, supprimant tous les attributs (y compris fanfaronConnecte)
        }
        // Redirige vers la page d'accueil (qui affichera le contenu "non connecté")
        response.sendRedirect(request.getContextPath() + "/accueil.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // La déconnexion se fait généralement via un GET, mais tu peux aussi gérer le POST si tu veux.
        doGet(request, response);
    }
}