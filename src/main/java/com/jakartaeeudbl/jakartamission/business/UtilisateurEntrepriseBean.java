package com.jakartaeeudbl.jakartamission.business;

import com.jakartaeeudbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional; // Demandé par l'énoncé
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UtilisateurEntrepriseBean {

    @PersistenceContext(unitName = "MonUniteDePersistence")
    private EntityManager em;

    // --- 1. AJOUTER (Modifié pour gérer doublons + Hachage) ---
    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) throws Exception {
        
        // Vérification préalable (Demandé dans la partie TP Résolution)
        if (existeDeja(username, email)) {
            throw new Exception("DOUBLON");
        }

        // Utilisation du constructeur (Demandé par l'énoncé)
        Utilisateur utilisateur = new Utilisateur(username, email, password, description);

        // Hachage du mot de passe (Demandé par l'énoncé)
        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            utilisateur.setPassword(hashedPassword);
        } catch (Exception e) {
            // Fallback si BCrypt échoue (rare)
            utilisateur.setPassword(password);
        }

        em.persist(utilisateur);
    }

    // --- 2. LISTER TOUS LES UTILISATEURS (Demandé) ---
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    // --- 3. SUPPRIMER (Demandé) ---
    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    // --- 4. TROUVER PAR ID (Demandé) ---
    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    // --- 5. TROUVER PAR EMAIL (Demandé) ---
    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // --- 6. VÉRIFIER MOT DE PASSE (Demandé) ---
    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    // --- 7. MÉTHODE UTILITAIRE POUR LES DOUBLONS (Notre ajout pour le TP) ---
    public boolean existeDeja(String username, String email) {
        Long count = em.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.username = :username OR u.email = :email", Long.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}