package com.jakartaeeudbl.jakartamission.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entité représentant une visite effectuée par un utilisateur dans un lieu.
 */
@Entity
@Table(name = "VISITE")
public class Visite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Date de la visite (stocke uniquement la date, sans l'heure si on veut)
    @Temporal(TemporalType.DATE)
    private Date dateVisite;

    // Un commentaire libre sur la visite
    private String commentaire;

    // RELATION 1: Plusieurs visites peuvent être faites par un seul utilisateur
    @ManyToOne
    @JoinColumn(name = "UTILISATEUR_ID")
    private Utilisateur utilisateur;

    // RELATION 2: Plusieurs visites peuvent concerner un seul lieu
    @ManyToOne
    @JoinColumn(name = "LIEU_ID")
    private Lieu lieu;

    // --- CONSTRUCTEUR VIDE (Obligatoire pour JPA) ---
    public Visite() {
    }

    // --- GETTERS ET SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    // --- MÉTHODES STANDARD (Pour l'affichage et la comparaison) ---

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Visite)) {
            return false;
        }
        Visite other = (Visite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jakartaeeudbl.jakartamission.entities.Visite[ id=" + id + " ]";
    }
}