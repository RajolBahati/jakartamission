package com.jakartaeeudbl.jakartamission;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.io.Serializable;

@Named(value = "navigationBean")
@RequestScoped
public class NavigationBean implements Serializable {

    // Retour vers l'accueil (qui est à la racine)
    public String allerAHome() {
        return "/home?faces-redirect=true";
    }

    // Retour vers le login (qui est à la racine)
    public String allerAIndex() {
        return "/index?faces-redirect=true";
    }

    // Vers A Propos (qui est MAINTENANT dans le dossier pages)
    public String voirApropos() {
        return "/pages/a_propos?faces-redirect=true"; 
    }
    
    // Vers Visite (Météo) (qui est MAINTENANT dans le dossier pages)
    public String allerAVisite() {
        return "/pages/Visite?faces-redirect=true";
    }
}