package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.*;
import com.example.fanfarehub.model.Evenement;
import com.example.fanfarehub.model.Fanfaron;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/evenements")
public class ListeEvenementServlet extends HttpServlet {

    private EvenementDao evenementDao;
    private GroupeDao groupeDao;


    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            evenementDao = new EvenementDaoImpl(connection);
            groupeDao = new GroupeDaoImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Fanfaron fanfaron = (session != null) ? (Fanfaron) session.getAttribute("fanfaronConnecte") : null;

        boolean estDansPrestation = false;
        if (fanfaron != null) {
            // Ajout du try-catch car findGroupeIdsByFanfaron peut lever une SQLException
            List<Integer> groupes = groupeDao.findGroupeIdsByFanfaron(fanfaron.getNomFanfaron());
            estDansPrestation = groupes.contains(1);
        }

        request.setAttribute("estDansPrestation", estDansPrestation);


        List<Evenement> evenements = evenementDao.findAll();
        request.setAttribute("evenements", evenements);
        request.getRequestDispatcher("/listeEvenements.jsp").forward(request, response);
    }
}