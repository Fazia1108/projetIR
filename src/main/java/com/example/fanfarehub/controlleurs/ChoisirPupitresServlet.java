package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.PupitreDao;
import com.example.fanfarehub.dao.PupitreDaoImpl;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Pupitre;
import com.example.fanfarehub.dao.DbConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/choisirPupitres")
public class ChoisirPupitresServlet extends HttpServlet {

    private PupitreDao pupitreDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            pupitreDao = new PupitreDaoImpl(connection);
        } catch (Exception e) {
            throw new ServletException("Erreur de connexion à la base de données", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron fanfaron = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;

        if (fanfaron == null) {
            response.sendRedirect(request.getContextPath() + "/connexion"); // page de login
            return;
        }

        List<Pupitre> pupitres = pupitreDao.findAll();
        List<Integer> pupitresSelectionnes = pupitreDao.findPupitreIdsByFanfaron(fanfaron.getNomFanfaron());

        String pupitreIdsStr = pupitresSelectionnes.stream()
                .map(id -> Integer.toString(id)) // évite l'ambiguïté de valueOf
                .collect(Collectors.joining(","));

        request.setAttribute("listePupitres", pupitres);
        request.setAttribute("pupitresSelectionnes", pupitresSelectionnes);
        request.setAttribute("pupitresSelectionnesStr", pupitreIdsStr);
        System.out.println("Nombre de pupitres : " + pupitres.size());


        request.getRequestDispatcher("/inscriptionPupitre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Fanfaron fanfaron = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;

        if (fanfaron == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        String nomFanfaron = fanfaron.getNomFanfaron();

        // Réinitialise les choix de pupitre
        pupitreDao.deleteFanfaronPupitres(nomFanfaron);

        // Récupération des pupitres sélectionnés (tableau de checkbox)
        String[] selectedPupitres = request.getParameterValues("pupitres");
        if (selectedPupitres != null) {
            for (String idPupitreStr : selectedPupitres) {
                try {
                    int idPupitre = Integer.parseInt(idPupitreStr);
                    pupitreDao.insertFanfaronPupitre(nomFanfaron, idPupitre);
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Log éventuellement
                }
            }
        }

        // Redirection ou message de confirmation
        response.sendRedirect(request.getContextPath() + "/accueil.jsp"); // ou une autre page de confirmation
    }
}
