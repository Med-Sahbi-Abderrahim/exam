package com.example.exam.servlet;

import com.example.exam.ejb.RendezVousEJB;
import com.example.exam.model.Patient;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/patientHome")
public class PatientHomeServlet extends HttpServlet {

    @EJB
    private RendezVousEJB rendezVousEJB;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        Patient p = (Patient) session.getAttribute("patient");
        if (p == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Long pid = p.getId();
        req.setAttribute("futureRdvs", rendezVousEJB.futureForPatient(pid));
        req.setAttribute("pastRdvs", rendezVousEJB.pastForPatient(pid));
        req.getRequestDispatcher("patientDashboard.jsp").forward(req, resp);
    }
}
