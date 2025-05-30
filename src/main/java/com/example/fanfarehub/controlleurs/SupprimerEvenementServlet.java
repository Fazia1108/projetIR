package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.EvenementDao;
import com.example.fanfarehub.dao.EvenementDaoImpl;
import com.example.fanfarehub.dao.InscriptionEvenementDao;
import com.example.fanfarehub.dao.InscriptionEvenementDaoImpl;
import com.example.fanfarehub.dao.DbConnectionManager;  // Ajouter l'import

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/supprimerEvenement")
public class SupprimerEvenementServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // Plus besoin de récupérer un DataSource
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idEvenementParam = req.getParameter("idEvenement");
        if (idEvenementParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "idEvenement manquant");
            return;
        }

        int idEvenement;
        try {
            idEvenement = Integer.parseInt(idEvenementParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "idEvenement invalide");
            return;
        }

        try (Connection connection = DbConnectionManager.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            InscriptionEvenementDao inscriptionDao = new InscriptionEvenementDaoImpl(connection);
            EvenementDao evenementDao = new EvenementDaoImpl(connection);

            inscriptionDao.deleteByEvenement(idEvenement);
            evenementDao.delete(idEvenement);

            connection.commit();

            resp.sendRedirect(req.getContextPath() + "/evenements");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la suppression");
        }
    }
}
