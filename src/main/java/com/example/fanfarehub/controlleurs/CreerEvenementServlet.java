package com.example.fanfarehub.controlleurs;

import com.example.fanfarehub.dao.DbConnectionManager;
import com.example.fanfarehub.dao.EvenementDao;
import com.example.fanfarehub.dao.EvenementDaoImpl;
import com.example.fanfarehub.dao.GroupeDao;
import com.example.fanfarehub.dao.GroupeDaoImpl;
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
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@WebServlet("/creerEvenement")
public class CreerEvenementServlet extends HttpServlet {

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

        if (fanfaron == null || !estDansCommissionPrestation(fanfaron)) {
            response.sendRedirect(request.getContextPath() + "/accueil.jsp");
            return;
        }

        request.getRequestDispatcher("/creerEvenement.jsp").forward(request, response);
    }

    private boolean estDansCommissionPrestation(Fanfaron fanfaron) {
        List<Integer> groupesIds = groupeDao.findGroupeIdsByFanfaron(fanfaron.getNomFanfaron());
        int ID_COMMISSION_PRESTATION = 1;
        return groupesIds.contains(ID_COMMISSION_PRESTATION);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String horodatageStr = request.getParameter("horodatage").replace("T", " ") + ":00";
        int heures = Integer.parseInt(request.getParameter("heures"));
        int minutes = Integer.parseInt(request.getParameter("minutes"));
        String dureeStr = request.getParameter("duree");
        String lieu = request.getParameter("lieu");
        String description = request.getParameter("description");
        int idType = Integer.parseInt(request.getParameter("type"));

        HttpSession session = request.getSession();
        Fanfaron fanfaron = (Fanfaron) session.getAttribute("fanfaronConnecte");

        Evenement evenement = new Evenement();
        evenement.setNomEvenement(nom);
        evenement.setHorodatage(Timestamp.valueOf(horodatageStr));
        evenement.setDuree(Duration.ofHours(heures).plusMinutes(minutes));        evenement.setLieu(lieu);
        evenement.setDescription(description);
        evenement.setIdTypeEvenement(idType);
        evenement.setNomFanfaronCreateur(fanfaron.getNomFanfaron());

        evenementDao.insert(evenement);

        response.sendRedirect(request.getContextPath() + "/evenements");
    }

}
