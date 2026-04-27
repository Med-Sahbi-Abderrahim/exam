package com.example.exam.servlet;

import com.example.exam.model.Patient;
import com.example.exam.service.PatientService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@WebServlet("/signupPatient")
public class SignupPatientServlet extends HttpServlet {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Patient p = new Patient();
        p.setNom(req.getParameter("nom"));
        p.setPrenom(req.getParameter("prenom"));
        p.setEmail(req.getParameter("email"));
        p.setPassword(req.getParameter("password"));
        p.setTelephone(req.getParameter("telephone"));

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        resp.sendRedirect("login.jsp");
    }
}