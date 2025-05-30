package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.EvenementDao;
import com.example.fanfarehub.dao.EvenementDaoImpl;
import com.example.fanfarehub.model.Evenement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/evenements")
public class ListeEvenementServlet extends HttpServlet {

    private EvenementDao evenementDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            evenementDao = new EvenementDaoImpl(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Evenement> evenements = evenementDao.findAll(); // À implémenter si pas déjà fait
        request.setAttribute("evenements", evenements);
        request.getRequestDispatcher("/listeEvenements.jsp").forward(request, response);
    }
}
