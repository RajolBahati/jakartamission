package com.jakartaeeudbl.jakartamission.business;

import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    public Utilisateur authentifier(String email, String password) {
        Utilisateur user = trouverUtilisateurParEmail(email);
        if (user != null && verifierMotDePasse(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean verifierMotDePasse(String passwordSaisi, String passwordHache) {
        try {
            return BCrypt.checkpw(passwordSaisi, passwordHache);
        } catch (Exception e) {
            return false;
        }
    }

    public void inscrire(Utilisateur utilisateur) {
        String hashed = BCrypt.hashpw(utilisateur.getPassword(), BCrypt.gensalt());
        utilisateur.setPassword(hashed);
        em.persist(utilisateur);
    }

    /**
     * Met à jour les informations de l'utilisateur (Description et/ou Mot de passe)
     */
    public void modifier(Utilisateur utilisateur) {
        // Si le mot de passe a été modifié (longueur courte = pas encore haché)
        if (utilisateur.getPassword() != null && utilisateur.getPassword().length() < 30) {
            String hashed = BCrypt.hashpw(utilisateur.getPassword(), BCrypt.gensalt());
            utilisateur.setPassword(hashed);
        }
        // merge met à jour l'entrée existante dans la base de données
        em.merge(utilisateur);
    }
}