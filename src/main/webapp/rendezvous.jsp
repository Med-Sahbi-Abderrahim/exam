<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String filter = (String) request.getAttribute("filter");
    if (filter == null) {
        filter = "today";
    }
    java.util.List<com.example.exam.model.RendezVous> rdvs =
            (java.util.List<com.example.exam.model.RendezVous>) request.getAttribute("rdvs");
%>
<html>
<head><title>Rendez-vous</title></head>
<body>

<h2>Rendez-vous</h2>

<p>
    <a href="<%= request.getContextPath() %>/rendezvous?action=listToday">Aujourd'hui</a>
    |
    <a href="<%= request.getContextPath() %>/rendezvous?action=listPast">Passes</a>
    |
    <a href="<%= request.getContextPath() %>/rendezvous?action=listAll">Tous</a>
    |
    <a href="<%= request.getContextPath() %>/rendezvous?action=createForm">Nouveau RDV</a>
</p>

<p><em>Filtre : <%= filter %></em></p>

<table border="1" cellpadding="4">
    <tr>
        <th>Date</th>
        <th>Patient</th>
        <th>Medecin</th>
        <th>Motif</th>
        <th>Statut</th>
        <th>Actions</th>
    </tr>
    <%
        if (rdvs != null) {
            for (com.example.exam.model.RendezVous r : rdvs) {
                String pnom = r.getPatient() != null ? r.getPatient().getNom() : "";
                String mnom = r.getMedecin() != null ? r.getMedecin().getNom() : "";
    %>
    <tr>
        <td><%= r.getDateRendezVous() %></td>
        <td><%= pnom %></td>
        <td><%= mnom %></td>
        <td><%= r.getMotif() != null ? r.getMotif() : "" %></td>
        <td><%= r.getStatut() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/rendezvous?action=cancel&amp;id=<%= r.getId() %>"
               onclick="return confirm('Annuler ce RDV ?');">Annuler</a>
            |
            <a href="<%= request.getContextPath() %>/rendezvous?action=rescheduleForm&amp;id=<%= r.getId() %>">Reporter</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<% if (rdvs == null || rdvs.isEmpty()) { %>
<p><em>Aucun rendez-vous.</em></p>
<% } %>

<p><a href="medecinDashboard.jsp">Dashboard medecin</a></p>

</body>
</html>
