package com.example.exam.service;

import com.example.exam.model.*;

import jakarta.persistence.*;
import java.util.List;

public class MedecinService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    // a. Ajouter
    public void addMedecin(Medecin m) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        em.close();
    }

    // b. Lister
    public List<Medecin> getAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Medecin m", Medecin.class).getResultList();
    }

    // c. Rechercher par spécialité
    public List<Medecin> searchBySpecialite(String spec) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                        "SELECT m FROM Medecin m WHERE m.specialite LIKE :s", Medecin.class)
                .setParameter("s", "%" + spec + "%")
                .getResultList();
    }

    // d. Patients d’un médecin
    public List<Patient> getPatients(Long medecinId) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                        "SELECT DISTINCT r.patient FROM RendezVous r WHERE r.medecin.id = :id",
                        Patient.class)
                .setParameter("id", medecinId)
                .getResultList();
    }

    // e. Supprimer médecin (si aucun rendez-vous)
    public void deleteMedecin(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Long count = em.createQuery(
                        "SELECT COUNT(r) FROM RendezVous r WHERE r.medecin.id = :id",
                        Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (count == 0) {
            Medecin m = em.find(Medecin.class, id);
            em.remove(m);
        } else {
            throw new RuntimeException("Médecin a des rendez-vous !");
        }

        em.getTransaction().commit();
        em.close();
    }
}