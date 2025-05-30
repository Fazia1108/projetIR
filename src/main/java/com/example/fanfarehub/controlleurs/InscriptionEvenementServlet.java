package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.InscriptionEvenementDao;
import com.example.fanfarehub.dao.InscriptionEvenementDaoImpl;
import com.example.fanfarehub.model.Fanfaron;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/inscriptionEvenement")
public class InscriptionEvenementServlet extends HttpServlet {

    private InscriptionEvenementDao inscriptionDao;

    @Override
    public void init() throws ServletException {
        try {
            inscriptionDao = new InscriptionEvenementDaoImpl(
                    DbConnectionManager.getInstance().getConnection()
            );
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation du DAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("fanfaronConnecte") == null) {
            response.sendRedirect(request.getContextPath() + "/connexion");
            return;
        }

        Fanfaron fanfaron = (Fanfaron) session.getAttribute("fanfaronConnecte");

        try {
            int idEvenement = Integer.parseInt(request.getParameter("idEvenement"));
            int idPupitre = Integer.parseInt(request.getParameter("pupitre"));
            int idStatut = Integer.parseInt(request.getParameter("statut"));

            inscriptionDao.upsert(fanfaron.getNomFanfaron(), idEvenement, idPupitre, idStatut);


            response.sendRedirect(request.getContextPath() + "/detailsEvenement?id=" + idEvenement);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres invalides.");
        }
    }
}
