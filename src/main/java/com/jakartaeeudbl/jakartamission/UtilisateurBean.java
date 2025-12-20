package com.jakartaeeudbl.jakartamission;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

// Import de votre EJB (Logique métier)
import com.jakartaeeudbl.jakartamission.business.UtilisateurEntrepriseBean;

@Named("utilisateurBean")
@RequestScoped
public class UtilisateurBean implements Serializable {

    // --- ATTRIBUTS AVEC VALIDATION ---
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit avoir entre 3 et 50 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
             message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial.")
    private String password;

    private String confirmPassword; // Pas de validation automatique ici, on le fait manuellement
    
    private String description;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    // --- MÉTHODE PRINCIPALE ---
    public void ajouterUtilisateur() {
        FacesContext context = FacesContext.getCurrentInstance();

        // 1. Vérification de la confirmation du mot de passe
        if (password != null && !password.equals(confirmPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas.", null));
            return; // On arrête tout si ça ne matche pas
        }

        try {
            // 2. Appel de la méthode métier (EJB)
            utilisateurEntrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);

            // 3. SUCCÈS : On affiche le message vert
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Utilisateur ajouté avec succès", null));

            // 4. On vide les champs du formulaire pour faire propre
            username = "";
            email = "";
            password = "";
            confirmPassword = "";
            description = "";
            
        } catch (Exception e) {
            // 5. GESTION DES ERREURS (Doublons ou autres)
            
            // On vérifie si l'erreur contient le mot clé "DOUBLON" envoyé par l'EJB
            if (e.getMessage() != null && e.getMessage().contains("DOUBLON")) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce nom d'utilisateur et cette adresse existent déjà.", null));
            } else {
                // Erreur technique imprévue
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur technique : " + e.getMessage(), null));
                e.printStackTrace(); // Pour le développeur (dans les logs)
            }
        }
    }

    // --- GETTERS & SETTERS (Standards) ---
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