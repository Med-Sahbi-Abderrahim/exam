package com.example.exam.servlet;

import com.example.exam.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/signupPatient")
public class SignupPatientServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Patient p = new Patient();
        p.setNom(req.getParameter("nom"));
        p.setPrenom(req.getParameter("prenom"));
        p.setEmail(req.getParameter("email"));
        p.setPassword(req.getParameter("password"));
        p.setTelephone(req.getParameter("telephone"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            resp.sendRedirect("login.jsp");
        } finally {
            em.close();
            emf.close();
        }
    }
}