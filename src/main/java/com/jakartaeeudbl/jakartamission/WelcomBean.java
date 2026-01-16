package com.jakartaeeudbl.jakartamission;

import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.business.SessionManager;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "welcomeBean")
@SessionScoped
public class WelcomBean implements Serializable {

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    private String email;
    private String password;
    private String newPassword;
    private Utilisateur utilisateurConnecte;

    public String sAuthentifier() {
        utilisateurConnecte = utilisateurEntrepriseBean.authentifier(email, password);

        if (utilisateurConnecte != null) {
            // Création de la session
            sessionManager.createSession("user", utilisateurConnecte.getEmail());
            
            // Redirection vers home.xhtml qui est à la racine
            return "/home?faces-redirect=true"; 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou mot de passe incorrect", null));
            return null;
        }
    }

    public String mettreAJourProfil() {
        try {
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                utilisateurConnecte.setPassword(newPassword);
            }
            utilisateurEntrepriseBean.modifier(utilisateurConnecte);
            newPassword = null; 
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil mis à jour avec succès !", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la mise à jour", null));
        }
        return null;
    }

    public String deconnecter() {
        sessionManager.invalidateSession();
        return "/index?faces-redirect=true";
    }

    // Getters et Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public Utilisateur getUtilisateurConnecte() { return utilisateurConnecte; }
}