package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.EvenementDao;
import com.example.fanfarehub.dao.EvenementDaoImpl;
import com.example.fanfarehub.model.Evenement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;

@WebServlet("/modifierEvenement")
public class ModifierEvenementServlet extends HttpServlet {

    private EvenementDao evenementDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            evenementDao = new EvenementDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEvenement = Integer.parseInt(request.getParameter("idEvenement"));
        Optional<Evenement> evenementOpt = evenementDao.findById(idEvenement);
        if (evenementOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Événement non trouvé");
            return;
        }

        // Passe l'objet Evenement (pas l'Optional) à la JSP
        request.setAttribute("evenement", evenementOpt.get());
        request.getRequestDispatcher("/modifierEvenement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEvenement = Integer.parseInt(request.getParameter("idEvenement"));
        String nom = request.getParameter("nom");
        String horodatageStr = request.getParameter("horodatage").replace("T", " ") + ":00";
        Timestamp horodatage = Timestamp.valueOf(horodatageStr);

        int heures = Integer.parseInt(request.getParameter("heures"));
        int minutes = Integer.parseInt(request.getParameter("minutes"));
        Duration duree = Duration.ofHours(heures).plusMinutes(minutes);

        String lieu = request.getParameter("lieu");
        String description = request.getParameter("description");
        int type = Integer.parseInt(request.getParameter("type"));

        Evenement evenement = new Evenement();
        evenement.setIdEvenement(idEvenement);
        evenement.setNomEvenement(nom);
        evenement.setHorodatage(horodatage);
        evenement.setDuree(Duration.ofHours(heures).plusMinutes(minutes));
        evenement.setLieu(lieu);
        evenement.setDescription(description);
        evenement.setIdTypeEvenement(type);

        evenementDao.update(evenement); // Assure-toi que cette méthode existe dans EvenementDaoImpl

        response.sendRedirect(request.getContextPath() + "/evenements");
    }
}
