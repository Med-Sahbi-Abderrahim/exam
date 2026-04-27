package com.example.exam.servlet;

import com.example.exam.model.Patient;
import com.example.exam.service.PatientService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {

    private PatientService service = new PatientService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "list";

        switch (action) {

            case "search":
                String keyword = request.getParameter("keyword");
                List<Patient> result = service.search(keyword);
                request.setAttribute("patients", result);
                request.getRequestDispatcher("patients.jsp").forward(request, response);
                break;

            case "edit":
                Long id = Long.parseLong(request.getParameter("id"));
                Patient p = service.getById(id);
                request.setAttribute("patient", p);
                request.getRequestDispatcher("form.jsp").forward(request, response);
                break;

            case "delete":
                Long idDelete = Long.parseLong(request.getParameter("id"));
                service.deletePatient(idDelete);
                response.sendRedirect("patients");
                break;

            default:
                List<Patient> patients = service.getAllPatients();
                request.setAttribute("patients", patients);
                request.getRequestDispatcher("patients.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");

        Patient p = new Patient();
        p.setNom(request.getParameter("nom"));
        p.setPrenom(request.getParameter("prenom"));
        p.setEmail(request.getParameter("email"));
        p.setTelephone(request.getParameter("telephone"));
        p.setDateNaissance(LocalDate.parse(request.getParameter("dateNaissance")));

        if (id == null || id.isEmpty()) {
            service.addPatient(p);
        } else {
            p.setId(Long.parseLong(id));
            service.updatePatient(p);
        }

        response.sendRedirect("patients");
    }
}