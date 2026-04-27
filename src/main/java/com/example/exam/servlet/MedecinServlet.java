package com.example.exam.servlet;

import com.example.exam.model.Medecin;
import com.example.exam.service.MedecinService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@WebServlet("/medecins")
public class MedecinServlet extends HttpServlet {

    private MedecinService service = new MedecinService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) action = "list";

        switch (action) {

            case "search":
                String spec = req.getParameter("spec");
                req.setAttribute("medecins", service.searchBySpecialite(spec));
                req.getRequestDispatcher("medecins.jsp").forward(req, resp);
                break;

            default:
                req.setAttribute("medecins", service.getAll());
                req.getRequestDispatcher("medecins.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Medecin m = new Medecin();
        m.setNom(req.getParameter("nom"));
        m.setPrenom(req.getParameter("prenom"));
        m.setSpecialite(req.getParameter("specialite"));
        m.setEmail(req.getParameter("email"));

        service.addMedecin(m);

        resp.sendRedirect("medecins");
    }
}