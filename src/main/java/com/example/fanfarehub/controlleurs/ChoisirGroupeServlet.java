package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.GroupeDao;
import com.example.fanfarehub.dao.GroupeDaoImpl;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Groupe;
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

@WebServlet("/choisirGroupes")
public class ChoisirGroupeServlet extends HttpServlet {

    private GroupeDao groupeDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            groupeDao = new GroupeDaoImpl(connection);
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
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        List<Groupe> groupes = groupeDao.findAll();
        List<Integer> groupesSelectionnes = groupeDao.findGroupeIdsByFanfaron(fanfaron.getNomFanfaron());

        String groupesIdsStr = groupesSelectionnes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        request.setAttribute("listeGroupes", groupes);
        request.setAttribute("groupesSelectionnes", groupesSelectionnes);
        request.setAttribute("groupesSelectionnesStr", groupesIdsStr);

        request.getRequestDispatcher("/inscriptionGroupe.jsp").forward(request, response);
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

        // Supprime les groupes précédemment sélectionnés
        groupeDao.deleteFanfaronGroupes(nomFanfaron);

        // Récupère les groupes sélectionnés
        String[] groupesSelectionnes = request.getParameterValues("groupes");
        if (groupesSelectionnes != null) {
            for (String idGroupeStr : groupesSelectionnes) {
                try {
                    int idGroupe = Integer.parseInt(idGroupeStr);
                    groupeDao.insertFanfaronGroupe(nomFanfaron, idGroupe);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // Redirection vers la page de compte ou confirmation
        response.sendRedirect(request.getContextPath() + "/accueil.jsp");
    }
}
