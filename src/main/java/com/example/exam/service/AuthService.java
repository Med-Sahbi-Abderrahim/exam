package com.example.exam.service;

import com.example.exam.model.*;
import jakarta.persistence.*;

public class AuthService {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    // PATIENT SIGNUP
    public void registerPatient(Patient p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    // MEDICAL SIGNUP
    public void registerMedecin(Medecin m) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        em.close();
    }

    // PATIENT LOGIN
    public Patient loginPatient(String email, String password) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT p FROM Patient p WHERE p.email = :e AND p.password = :p",
                            Patient.class)
                    .setParameter("e", email)
                    .setParameter("p", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // MEDIC LOGIN
    public Medecin loginMedecin(String email, String password) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT m FROM Medecin m WHERE m.email = :e AND m.password = :p",
                            Medecin.class)
                    .setParameter("e", email)
                    .setParameter("p", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}