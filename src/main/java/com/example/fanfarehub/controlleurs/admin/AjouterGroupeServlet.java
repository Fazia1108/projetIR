package com.example.fanfarehub.controlleurs.admin;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.GroupeDaoImpl;
import com.example.fanfarehub.model.Groupe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/admin/ajouterGroupe")
public class AjouterGroupeServlet extends HttpServlet {

    private GroupeDaoImpl groupeDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            groupeDao = new GroupeDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation de GroupeDao", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomGroupe = request.getParameter("nomGroupe");

        Groupe groupe = new Groupe();
        groupe.setNomGroupe(nomGroupe);

        try {
            groupeDao.insertGroupe(groupe);
            request.getSession().setAttribute("messageSucces", "Le groupe a été ajouté.");
        } catch (Exception e) {
            request.getSession().setAttribute("messageErreur", "Erreur lors de l'ajout.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/groupes");
    }
}
