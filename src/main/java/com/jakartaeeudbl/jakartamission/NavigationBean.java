package com.jakartaeeudbl.jakartamission;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import java.io.Serializable;

@Named(value = "navigationBean")
@RequestScoped
public class NavigationBean implements Serializable {

    // Cette méthode est appelée par le bouton "À propos"
    public String voirApropos() {
        // Renvoie le nom de la page destination (sans l'extension .xhtml)
        return "a_propos"; 
    }
}