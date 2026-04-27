package com.example.exam.service;

import com.example.exam.model.Patient;
import com.example.exam.model.RendezVous;

import jakarta.persistence.*;
import java.util.List;

public class PatientService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    // 🔹 a. Lister
    public List<Patient> getAllPatients() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    // 🔹 b. Ajouter
    public void addPatient(Patient p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    // 🔹 c. Rechercher
    public List<Patient> search(String keyword) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                        "SELECT p FROM Patient p WHERE p.nom LIKE :k OR p.email LIKE :k", Patient.class)
                .setParameter("k", "%" + keyword + "%")
                .getResultList();
    }

    // 🔹 d. Modifier
    public void updatePatient(Patient p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }

    // 🔹 e. Supprimer (condition)
    public void deletePatient(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Patient p = em.find(Patient.class, id);

        Long count = em.createQuery(
                        "SELECT COUNT(r) FROM RendezVous r WHERE r.patient.id = :id AND r.statut = 'PLANIFIE'",
                        Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (count == 0) {
            em.remove(p);
        } else {
            throw new RuntimeException("Patient a des rendez-vous actifs !");
        }

        em.getTransaction().commit();
        em.close();
    }

    public Patient getById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Patient.class, id);
    }
}