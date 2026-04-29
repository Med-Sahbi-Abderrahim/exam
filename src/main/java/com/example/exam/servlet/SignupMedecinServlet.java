package com.example.exam.servlet;

import com.example.exam.model.Medecin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signupMedecin")
public class SignupMedecinServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Medecin m = new Medecin();
        m.setNom(req.getParameter("nom"));
        m.setPrenom(req.getParameter("prenom"));
        m.setEmail(req.getParameter("email"));
        m.setPassword(req.getParameter("password"));
        m.setSpecialite(req.getParameter("specialite"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            resp.sendRedirect("login.jsp");
        } finally {
            em.close();
            emf.close();
        }
    }
}