package com.jakartaeeudbl.jakartamission;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;

    // Méthode appelée lors du clic sur le bouton "Ajouter"
    public String ajouterLieu() {
        // Ici, vous mettrez plus tard le code pour sauvegarder en base de données
        System.out.println("Ajout du lieu : " + nom + " (" + latitude + ", " + longitude + ")");
        
        // Après l'ajout, on retourne vers la page d'accueil (ou on reste sur la même page)
        return "home"; 
    }

    // --- Getters et Setters (Obligatoires pour JSF) ---

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}