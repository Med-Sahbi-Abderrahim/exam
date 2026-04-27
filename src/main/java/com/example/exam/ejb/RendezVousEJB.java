package com.example.exam.ejb;

import com.example.exam.model.*;

import jakarta.ejb.Stateful;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Stateful
public class RendezVousEJB {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    // a. Créer rendez-vous
    public void createRdv(Patient p, Medecin m, LocalDateTime date, String motif) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        RendezVous r = new RendezVous();
        r.setPatient(p);
        r.setMedecin(m);
        r.setDateRendezVous(date);
        r.setMotif(motif);
        r.setStatut(Statut.PLANIFIE);

        em.persist(r);

        em.getTransaction().commit();
        em.close();
    }

    // b. Annuler
    public void cancel(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        RendezVous r = em.find(RendezVous.class, id);
        r.setStatut(Statut.ANNULE);

        em.getTransaction().commit();
        em.close();
    }

    // c. Modifier horaire
    public void updateDate(Long id, LocalDateTime newDate) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        RendezVous r = em.find(RendezVous.class, id);
        r.setDateRendezVous(newDate);

        em.getTransaction().commit();
        em.close();
    }

    // d. Rendez-vous du jour
    public List<RendezVous> today() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery(
                        "SELECT r FROM RendezVous r WHERE DATE(r.dateRendezVous) = CURRENT_DATE",
                        RendezVous.class)
                .getResultList();
    }

    // e. Rendez-vous passés
    public List<RendezVous> past() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery(
                        "SELECT r FROM RendezVous r WHERE r.dateRendezVous < CURRENT_TIMESTAMP",
                        RendezVous.class)
                .getResultList();
    }
}