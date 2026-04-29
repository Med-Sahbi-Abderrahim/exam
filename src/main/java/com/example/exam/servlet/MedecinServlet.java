package com.example.exam.servlet;

import com.example.exam.model.Medecin;
import com.example.exam.service.MedecinService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/medecins")
public class MedecinServlet extends HttpServlet {

    private final MedecinService service = new MedecinService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {

            case "search":
                String spec = req.getParameter("spec");
                req.setAttribute("medecins", service.searchBySpecialite(spec));
                req.getRequestDispatcher("medecins.jsp").forward(req, resp);
                break;

            case "patients":
                Long medId = Long.parseLong(req.getParameter("id"));
                Medecin medecin = service.findById(medId);
                if (medecin == null) {
                    resp.sendRedirect("medecins");
                    return;
                }
                req.setAttribute("medecin", medecin);
                req.setAttribute("doctorPatients", service.getPatients(medId));
                req.getRequestDispatcher("medecinPatients.jsp").forward(req, resp);
                break;

            case "delete":
                try {
                    service.deleteMedecin(Long.parseLong(req.getParameter("id")));
                } catch (RuntimeException ex) {
                    resp.sendRedirect("medecins?error=delete");
                    return;
                }
                resp.sendRedirect("medecins");
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
