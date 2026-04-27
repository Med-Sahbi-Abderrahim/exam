package com.example.exam.servlet;

import com.example.exam.model.Medecin;
import com.example.exam.model.Patient;
import com.example.exam.service.AuthService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/signupMedecin")
public class SignupMedecinServlet extends HttpServlet {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Medecin m = new Medecin();
        m.setNom(req.getParameter("nom"));
        m.setPrenom(req.getParameter("prenom"));
        m.setEmail(req.getParameter("email"));
        m.setPassword(req.getParameter("password"));
        m.setSpecialite(req.getParameter("specialite"));

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

        resp.sendRedirect("login.jsp");
    }
}