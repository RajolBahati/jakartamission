package com.jakartaeeudbl.jakartamission;

import com.jakartaeeudbl.jakartamission.entities.Lieu;
import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import com.jakartaeeudbl.jakartamission.entities.Visite;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named(value = "visiteBean")
@SessionScoped
public class VisiteBean implements Serializable {

    @Inject
    private WelcomBean welcomeBean; // Pour l'utilisateur connecté
    
    @Inject
    private LieuBean lieuBean; // <--- NOUVEAU : On récupère la liste des vrais lieux

    private Long selectedLieuId;
    private String weatherInfo = "En attente de sélection d'un lieu...";
    private Visite nouvelleVisite = new Visite();
    private List<Visite> listeVisites = new ArrayList<>();

    // --- 1. Méthode Météo ---
    public void chargerMeteo() {
        if (selectedLieuId != null) {
            this.weatherInfo = "Météo pour le lieu ID " + selectedLieuId + " : 28°C, Ensoleillé (Donnée API)";
        } else {
            this.weatherInfo = "Aucun lieu sélectionné";
        }
    }

    // --- 2. Méthode Enregistrer (CORRIGÉE) ---
    public String enregistrerVisite() {
        // 1. Attacher l'utilisateur
        Utilisateur user = welcomeBean.getUtilisateurConnecte();
        nouvelleVisite.setUtilisateur(user);

        // 2. Retrouver le VRAI lieu grâce à l'ID sélectionné
        if (selectedLieuId != null) {
            for (Lieu l : lieuBean.getListeLieux()) {
                if (l.getId().equals(selectedLieuId)) {
                    // On a trouvé la vraie ville (ex: Bali), on l'attache à la visite
                    nouvelleVisite.setLieu(l);
                    break;
                }
            }
        }

        // 3. Gérer la date
        if (nouvelleVisite.getDateVisite() == null) {
            nouvelleVisite.setDateVisite(new Date());
        }

        // 4. Ajouter à la liste
        listeVisites.add(nouvelleVisite);

        // 5. Reset
        nouvelleVisite = new Visite();
        
        return null;
    }

    // --- Getters et Setters ---
    public Long getSelectedLieuId() { return selectedLieuId; }
    public void setSelectedLieuId(Long selectedLieuId) { this.selectedLieuId = selectedLieuId; }

    public String getWeatherInfo() { return weatherInfo; }
    public void setWeatherInfo(String weatherInfo) { this.weatherInfo = weatherInfo; }

    public Visite getNouvelleVisite() { return nouvelleVisite; }
    public void setNouvelleVisite(Visite nouvelleVisite) { this.nouvelleVisite = nouvelleVisite; }

    public List<Visite> getListeVisites() { return listeVisites; }
}