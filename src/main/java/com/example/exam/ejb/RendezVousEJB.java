package com.example.exam.ejb;

import com.example.exam.model.*;
import com.example.exam.rmi.NotificationRegistry;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateful
public class RendezVousEJB {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @EJB
    private NotificationRegistry notificationRegistry;

    public void createRdv(Long patientId, Long medecinId, LocalDateTime date, String motif) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Patient p = em.find(Patient.class, patientId);
            Medecin m = em.find(Medecin.class, medecinId);
            RendezVous r = new RendezVous();
            r.setPatient(p);
            r.setMedecin(m);
            r.setDateRendezVous(date);
            r.setMotif(motif);
            r.setStatut(Statut.PLANIFIE);
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        notificationRegistry.notifyPatient(Math.toIntExact(patientId), "RDV cree");
    }

    public void cancel(Long id) {
        Long patientId = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RendezVous r = em.find(RendezVous.class, id);
            if (r != null) {
                if (r.getPatient() != null) {
                    patientId = r.getPatient().getId();
                }
                r.setStatut(Statut.ANNULE);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        if (patientId != null) {
            notificationRegistry.notifyPatient(Math.toIntExact(patientId), "RDV annule");
        }
    }

    public void updateDate(Long id, LocalDateTime newDate) {
        Long patientId = null;
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RendezVous r = em.find(RendezVous.class, id);
            if (r != null) {
                if (r.getPatient() != null) {
                    patientId = r.getPatient().getId();
                }
                r.setDateRendezVous(newDate);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        if (patientId != null) {
            notificationRegistry.notifyPatient(Math.toIntExact(patientId), "RDV replanifie");
        }
    }

    public List<RendezVous> today() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin "
                                    + "WHERE r.dateRendezVous >= :start AND r.dateRendezVous < :end ORDER BY r.dateRendezVous",
                            RendezVous.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RendezVous> past() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin "
                                    + "WHERE r.dateRendezVous < CURRENT_TIMESTAMP ORDER BY r.dateRendezVous DESC",
                            RendezVous.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RendezVous> allOrdered() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin ORDER BY r.dateRendezVous DESC",
                            RendezVous.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RendezVous> futureForPatient(Long patientId) {
        EntityManager em = emf.createEntityManager();
        try {
            LocalDateTime now = LocalDateTime.now();
            return em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin "
                                    + "WHERE r.patient.id = :pid AND r.dateRendezVous >= :now ORDER BY r.dateRendezVous",
                            RendezVous.class)
                    .setParameter("pid", patientId)
                    .setParameter("now", now)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RendezVous> pastForPatient(Long patientId) {
        EntityManager em = emf.createEntityManager();
        try {
            LocalDateTime now = LocalDateTime.now();
            return em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin "
                                    + "WHERE r.patient.id = :pid AND r.dateRendezVous < :now ORDER BY r.dateRendezVous DESC",
                            RendezVous.class)
                    .setParameter("pid", patientId)
                    .setParameter("now", now)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
