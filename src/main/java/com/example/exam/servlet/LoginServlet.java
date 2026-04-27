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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        HttpSession session = req.getSession();

        EntityManager em = emf.createEntityManager();

        try {

            if (role.equals("patient")) {

                Patient p = em.createQuery(
                                "SELECT p FROM Patient p WHERE p.email=:e AND p.password=:p",
                                Patient.class)
                        .setParameter("e", email)
                        .setParameter("p", password)
                        .getSingleResult();

                session.setAttribute("patient", p);
                resp.sendRedirect("patientDashboard.jsp");

            } else {

                Medecin m = em.createQuery(
                                "SELECT m FROM Medecin m WHERE m.email=:e AND m.password=:p",
                                Medecin.class)
                        .setParameter("e", email)
                        .setParameter("p", password)
                        .getSingleResult();

                session.setAttribute("medecin", m);
                resp.sendRedirect("medecinDashboard.jsp");
            }

        } catch (Exception e) {
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}