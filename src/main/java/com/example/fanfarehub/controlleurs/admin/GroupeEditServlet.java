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

@WebServlet("/admin/modifierGroupe")
public class GroupeEditServlet extends HttpServlet {

    private GroupeDaoImpl groupeDao;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DbConnectionManager.getInstance().getConnection();
            groupeDao = new GroupeDaoImpl(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur d'initialisation du GroupeDao", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idGroupe");
        String nomGroupe = request.getParameter("nomGroupe");

        try {
            int id = Integer.parseInt(idStr);
            Groupe groupe = new Groupe();
            groupe.setNomGroupe(nomGroupe);
            groupeDao.updateGroupe(id, groupe);
            request.getSession().setAttribute("messageSucces", "Le groupe a été modifié.");
        } catch (Exception e) {
            request.getSession().setAttribute("messageErreur", "Erreur lors de la modification.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/groupes");
    }
}
