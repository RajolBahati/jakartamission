package com.jakartaeeudbl.jakartamission;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import com.jakartaeeudbl.jakartamission.business.LieuEntrepriseBean;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    private Lieu lieu = new Lieu();

    public LieuBean() {
    }

    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }

    // Pour afficher la liste dans le tableau
    public List<Lieu> getListeLieux() {
        return lieuEntrepriseBean.listerTousLesLieux();
    }

    // SAUVEGARDER (Gère Création ET Modification)
    public String sauvegarder() {
        if (lieu.getId() == null) {
            lieuEntrepriseBean.ajouterLieu(lieu); // Nouveau
        } else {
            lieuEntrepriseBean.modifierLieu(lieu); // Existant
        }
        lieu = new Lieu(); // Reset
        return "visiter?faces-redirect=true"; // Retourne à la liste
    }

    // PRÉPARER MODIFICATION (Charge les données dans le formulaire)
    public String chargerPourModification(Lieu l) {
        this.lieu = l;
        return "lieu"; // Va vers la page du formulaire
    }

    // SUPPRIMER
    public String supprimer(Long id) {
        lieuEntrepriseBean.supprimerLieu(id);
        return "visiter?faces-redirect=true"; // Recharge la page
    }
}