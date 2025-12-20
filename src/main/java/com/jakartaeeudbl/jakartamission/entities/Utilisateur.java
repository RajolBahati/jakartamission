package com.jakartaeeudbl.jakartamission.entities; // ou votre package

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'énoncé parle de contraintes @Column
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String description;

    // 1. Constructeur par défaut (OBLIGATOIRE pour JPA)
    public Utilisateur() {}

    // 2. Constructeur avec arguments (Demandé par l'énoncé)
    public Utilisateur(String username, String email, String password, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    // Getters et Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}