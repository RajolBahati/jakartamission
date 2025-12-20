package com.jakartaeeudbl.jakartamission.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LIEU")
public class Lieu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ATTENTION ICI : Utilisez Long (L majuscule) et pas int
    private Long id; 

    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;

    public Lieu() {
    }

    // Getters et Setters mis à jour avec Long

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    @Override
    public String toString() {
        return "Lieu[ id=" + id + " ]";
    }
}