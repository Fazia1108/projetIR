package com.example.fanfarehub.controller;

import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.util.PasswordHasher;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Named
@RequestScoped
public class ConnexionBean {

    @Inject
    private FanfaronDao fanfaronDao;
    @Inject
    private PasswordHasher passwordHasher;

    private String nomFanfaron;
    private String motDePasse;

    // Getters et setters pour nomFanfaron et motDePasse

    public String getNomFanfaron() { return nomFanfaron; }
    public void setNomFanfaron(String nomFanfaron) { this.nomFanfaron = nomFanfaron; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String connecter() {
        Optional<Fanfaron> fanfaronOptional = fanfaronDao.findByNomFanfaron(nomFanfaron);
        if (fanfaronOptional.isPresent()) {
            Fanfaron fanfaron = fanfaronOptional.get();
            if (passwordHasher.checkPassword(motDePasse, fanfaron.getMotDePasse())) {
                fanfaron.setDerniereConnexion(LocalDateTime.now());
                fanfaronDao.update(fanfaron);
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("nomFanfaronConnecte", nomFanfaron);
                return "accueil?faces-redirect=true";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nom de fanfaron ou mot de passe incorrect.", null));
        return null;
    }
}