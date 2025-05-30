package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.EvenementDao;
import com.example.fanfarehub.dao.EvenementDaoImpl;
import com.example.fanfarehub.dao.InscriptionEvenementDao;
import com.example.fanfarehub.dao.InscriptionEvenementDaoImpl;
import com.example.fanfarehub.model.Evenement;
import com.example.fanfarehub.model.dto.InscriptionDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/detailsEvenement")
public class DetailsEvenementServlet extends HttpServlet {

    private EvenementDao evenementDao;
    private InscriptionEvenementDao inscriptionDao;

    @Override
    public void init() throws ServletException {
        try {
            var connection = com.example.fanfarehub.dao.DbConnectionManager.getInstance().getConnection();
            evenementDao = new EvenementDaoImpl(connection);
            inscriptionDao = new InscriptionEvenementDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur d'accès à la base de données", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paramId = request.getParameter("id");
        if (paramId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre id manquant.");
            return;
        }

        int idEvenement;
        try {
            idEvenement = Integer.parseInt(paramId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identifiant invalide.");
            return;
        }

        Optional<Evenement> evtOpt = evenementDao.findById(idEvenement);
        if (evtOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Événement introuvable.");
            return;
        }

        Evenement evenement = evtOpt.get();

        // Récupérer les inscriptions liées à cet événement
        List<InscriptionDTO> inscriptions = inscriptionDao.findByEvenementGrouped(idEvenement);

        request.setAttribute("evenement", evenement);
        request.setAttribute("inscriptions", inscriptions);

        // Forward vers JSP
        request.getRequestDispatcher("/detailsEvenement.jsp").forward(request, response);
    }
}
