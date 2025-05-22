package com.example.fanfarehub.controller;

import com.example.fanfarehub.dao.FanfaronDao;
import com.example.fanfarehub.dao.GenreDao;
import com.example.fanfarehub.dao.ContrainteAlimentaireDao;
import com.example.fanfarehub.model.Fanfaron;
import com.example.fanfarehub.model.Genre;
import com.example.fanfarehub.model.ContrainteAlimentaire;
import com.example.fanfarehub.util.PasswordHasher;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Named
@RequestScoped
public class InscriptionBean {

    @Inject
    private FanfaronDao fanfaronDao;
    @Inject
    private GenreDao genreDao;
    @Inject
    private ContrainteAlimentaireDao contrainteAlimentaireDao;
    @Inject
    private PasswordHasher passwordHasher;

    private Fanfaron fanfaron = new Fanfaron();
    private String confirmationEmail;
    private String motDePasse;
    private String confirmationMotDePasse;

    // Getters et setters
    public Fanfaron getFanfaron() { return fanfaron; }
    public void setFanfaron(Fanfaron fanfaron) { this.fanfaron = fanfaron; }
    public String getConfirmationEmail() { return confirmationEmail; }
    public void setConfirmationEmail(String confirmationEmail) { this.confirmationEmail = confirmationEmail; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getConfirmationMotDePasse() { return confirmationMotDePasse; }
    public void setConfirmationMotDePasse(String confirmationMotDePasse) { this.confirmationMotDePasse = confirmationMotDePasse; }

    public String inscrire() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!fanfaron.getEmail().equals(confirmationEmail)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les adresses email ne correspondent pas.", null));
            return null;
        }
        if (!motDePasse.equals(confirmationMotDePasse)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas.", null));
            return null;
        }
        if (fanfaronDao.findByNomFanfaron(fanfaron.getNomFanfaron()).isPresent()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce nom de fanfaron est déjà utilisé.", null));
            return null;
        }
        if (fanfaronDao.findByEmail(fanfaron.getEmail()).isPresent()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette adresse email est déjà utilisée.", null));
            return null;
        }

        String hashedPassword = passwordHasher.hash(motDePasse);
        fanfaron.setMotDePasse(hashedPassword);
        fanfaron.setDateCreation(LocalDateTime.now());

        Integer idGenre = fanfaron.getGenre().getId();
        Optional<Genre> genreOpt = genreDao.findById(idGenre);
        if (genreOpt.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Genre invalide.", null));
            return null;
        }
        fanfaron.setGenre(genreOpt.get());

        Integer idContrainte = fanfaron.getContraintesAlimentaires().getIdContrainteAlimentaire();
        Optional<ContrainteAlimentaire> contrainteOpt = contrainteAlimentaireDao.findById(idContrainte);
        if (contrainteOpt.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contrainte alimentaire invalide.", null));
            return null;
        }
        fanfaron.setContraintesAlimentaires(contrainteOpt.get());

        fanfaronDao.create(fanfaron);

        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.setAttribute("nomFanfaronConnecte", fanfaron.getNomFanfaron());

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscription réussie. Vous êtes maintenant connecté.", null));
        return "accueil?faces-redirect=true";
    }

}
