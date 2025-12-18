package com.jakartaeeudbl.jakartamission.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.jakartaeeudbl.jakartamission.Lieu;
import java.util.List;

@Stateless
public class LieuEntrepriseBean {

    @PersistenceContext(unitName = "monUniteDePersistence")
    private EntityManager em;

    public void ajouterLieu(Lieu lieu) {
        em.persist(lieu);
    }

    public void modifierLieu(Lieu lieu) {
        em.merge(lieu);
    }

    public void supprimerLieu(Long id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }

    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT l FROM Lieu l", Lieu.class).getResultList();
    }
}