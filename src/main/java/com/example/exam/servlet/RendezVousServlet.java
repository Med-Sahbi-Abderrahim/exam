package com.example.exam.servlet;

import com.example.exam.ejb.RendezVousEJB;
import com.example.exam.model.RendezVous;
import com.example.exam.service.MedecinService;
import com.example.exam.service.PatientService;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/rendezvous")
public class RendezVousServlet extends HttpServlet {

    @EJB
    private RendezVousEJB rendezVousEJB;

    private final PatientService patientService = new PatientService();
    private final MedecinService medecinService = new MedecinService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "listToday";
        }

        switch (action) {

            case "listToday":
                forwardList(req, resp, rendezVousEJB.today(), "today");
                break;

            case "listPast":
                forwardList(req, resp, rendezVousEJB.past(), "past");
                break;

            case "listAll":
                forwardList(req, resp, rendezVousEJB.allOrdered(), "all");
                break;

            case "createForm":
                req.setAttribute("patients", patientService.getAllPatients());
                req.setAttribute("medecins", medecinService.getAll());
                req.getRequestDispatcher("rendezvousForm.jsp").forward(req, resp);
                break;

            case "rescheduleForm":
                Long rid = Long.parseLong(req.getParameter("id"));
                req.setAttribute("rdvId", rid);
                req.getRequestDispatcher("rendezvousReschedule.jsp").forward(req, resp);
                break;

            case "cancel":
                rendezVousEJB.cancel(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath() + "/rendezvous?action=listAll");
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/rendezvous?action=listToday");
        }
    }

    private void forwardList(HttpServletRequest req, HttpServletResponse resp, List<RendezVous> rdvs, String filter)
            throws ServletException, IOException {
        req.setAttribute("rdvs", rdvs);
        req.setAttribute("filter", filter);
        req.getRequestDispatcher("rendezvous.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            Long patientId = Long.parseLong(req.getParameter("patientId"));
            Long medecinId = Long.parseLong(req.getParameter("medecinId"));
            LocalDateTime when = LocalDateTime.parse(req.getParameter("dateRendezVous"));
            String motif = req.getParameter("motif");
            if (motif == null) {
                motif = "";
            }
            rendezVousEJB.createRdv(patientId, medecinId, when, motif);
            resp.sendRedirect(req.getContextPath() + "/rendezvous?action=listAll");
            return;
        }

        if ("reschedule".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            LocalDateTime newDate = LocalDateTime.parse(req.getParameter("newDate"));
            rendezVousEJB.updateDate(id, newDate);
            resp.sendRedirect(req.getContextPath() + "/rendezvous?action=listAll");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/rendezvous?action=listToday");
    }
}
