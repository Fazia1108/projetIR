package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.InscriptionEvenementDao;
import com.example.fanfarehub.dao.InscriptionEvenementDaoImpl;
import com.example.fanfarehub.model.dto.InscriptionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/detailsEvenement")
public class DetailsEvenementServlet extends HttpServlet {

    private InscriptionEvenementDao inscriptionDao;

    @Override
    public void init() {
        try {
            inscriptionDao = new InscriptionEvenementDaoImpl(DbConnectionManager.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idEvenement = Integer.parseInt(request.getParameter("id"));
        List<InscriptionDTO> inscriptions = inscriptionDao.findByEvenementGrouped(idEvenement);

        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("/detailsEvenement.jsp").forward(request, response);
    }
}
