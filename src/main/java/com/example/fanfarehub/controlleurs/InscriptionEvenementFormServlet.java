package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.*;
import com.example.fanfarehub.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/inscriptionEvenementForm")
public class InscriptionEvenementFormServlet extends HttpServlet {

    private EvenementDao evenementDao;
    private PupitreDao pupitreDao;
    private StatutDao statutDao;

    @Override
    public void init() throws ServletException {
        try {
            var connection = DbConnectionManager.getInstance().getConnection();
            evenementDao = new EvenementDaoImpl(connection);
            pupitreDao = new PupitreDaoImpl(connection);
            statutDao = new StatutDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur d'accès à la base", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEvenement = Integer.parseInt(request.getParameter("idEvenement"));

        Optional<Evenement> evtOpt = evenementDao.findById(idEvenement);
        if (evtOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Événement introuvable");
            return;
        }

        HttpSession session = request.getSession(false);
        Fanfaron fanfaron = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;
        if (fanfaron == null) {
            request.setAttribute("fanfaronConnecte", null);
        } else {
            List<Pupitre> pupitres = pupitreDao.findByFanfaron(fanfaron.getNomFanfaron());
            request.setAttribute("fanfaronConnecte", fanfaron);
            request.setAttribute("listePupitres", pupitres);
        }

        List<Statut> statuts = statutDao.findAll();

        request.setAttribute("evenement", evtOpt.get());
        request.setAttribute("listeStatuts", statuts);

        request.getRequestDispatcher("/inscriptionEvenement.jsp").forward(request, response);
    }
}
