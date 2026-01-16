package com.jakartaeeudbl.jakartamission;

import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaeeudbl.jakartamission.entities.Lieu;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    private Lieu lieu = new Lieu(); 
    private Long selectedLieu; // Changé en Long pour correspondre à l'ID de l'Entité
    private String weatherMessage;
    private List<Lieu> listeLieux;

    @PostConstruct
    public void init() {
        this.listeLieux = lieuEntrepriseBean.listerTousLesLieux();
    }

    public String sauvegarder() {
        if (lieu.getId() == null) {
            lieuEntrepriseBean.ajouterLieu(lieu);
        } else {
            lieuEntrepriseBean.modifierLieu(lieu);
        }
        return "visiter?faces-redirect=true";
    }

    public String supprimer(Long id) {
        lieuEntrepriseBean.supprimerLieu(id);
        return "visiter?faces-redirect=true";
    }

    public String chargerPourModification(Lieu l) {
        this.lieu = l;
        return "lieu";
    }

    public void updateWeatherMessage(AjaxBehaviorEvent event) {
        if (selectedLieu != null) {
            Lieu l = lieuEntrepriseBean.trouverLieuParId(selectedLieu);
            if (l != null) {
                fetchWeatherMessage(l);
            }
        }
    }

    private void fetchWeatherMessage(Lieu l) {
        try {
            String serviceURL = "http://localhost:8080/j-weather/webapi/JarkartaWeather?lat="
                    + l.getLatitude() + "&long=" + l.getLongitude();
            Client client = ClientBuilder.newClient();
            this.weatherMessage = client.target(serviceURL)
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);
        } catch (Exception e) {
            this.weatherMessage = "Service meteo indisponible";
        }
    }

    // --- CORRECTION DE LA LIGNE 64 ICI ---
    public void redirection(String destination) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(destination);
        } catch (IOException ex) {
            // Correction de la parenthèse : getName() doit être fermé avant .log
            Logger.getLogger(LieuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void voirVisite() {
        this.redirection("Visite.xhtml");
    }

    // Getters et Setters
    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }
    public Long getSelectedLieu() { return selectedLieu; }
    public void setSelectedLieu(Long selectedLieu) { this.selectedLieu = selectedLieu; }
    public String getWeatherMessage() { return weatherMessage; }
    public List<Lieu> getListeLieux() { return listeLieux; }
}