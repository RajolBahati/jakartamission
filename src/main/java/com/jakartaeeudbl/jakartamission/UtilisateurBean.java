package com.jakartaeeudbl.jakartamission;

import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named(value = "utilisateurBean")
@RequestScoped
public class UtilisateurBean implements Serializable {

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    // Champs du formulaire
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String description;

    public String ajouterUtilisateur() {
        try {
            // 1. Vérification des mots de passe
            if (!password.equals(confirmPassword)) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas", null));
                return null;
            }

            // 2. Création de l'objet
            Utilisateur nouvelUtilisateur = new Utilisateur();
            
            // --- CORRECTION DE L'ERREUR DE COMPILATION ICI ---
            // On remplace setNomUtilisateur par setUsername
            nouvelUtilisateur.setUsername(this.username); 
            // -------------------------------------------------
            
            nouvelUtilisateur.setEmail(this.email);
            nouvelUtilisateur.setPassword(this.password);
            nouvelUtilisateur.setDescription(this.description);

            // 3. Enregistrement via l'EJB
            utilisateurEntrepriseBean.inscrire(nouvelUtilisateur);

            // 4. Message de succès et redirection
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Compte créé ! Connectez-vous.", null));
            
            return "/index?faces-redirect=true";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'inscription (Email déjà pris ?)", null));
            return null;
        }
    }

    // --- GETTERS ET SETTERS ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}