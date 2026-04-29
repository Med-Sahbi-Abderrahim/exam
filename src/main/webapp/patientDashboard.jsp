<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.example.exam.model.Patient" %>
<%@ page import="com.example.exam.model.RendezVous" %>
<%@ page import="java.util.List" %>
<%
    Patient p = (Patient) session.getAttribute("patient");
    if (p == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    if (request.getAttribute("futureRdvs") == null) {
        response.sendRedirect(request.getContextPath() + "/patientHome");
        return;
    }
    List<RendezVous> futureRdvs = (List<RendezVous>) request.getAttribute("futureRdvs");
    List<RendezVous> pastRdvs = (List<RendezVous>) request.getAttribute("pastRdvs");
%>

<html>
<head>
    <title>Patient Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<h2>Welcome, <%= p.getNom() %></h2>

<h3>Rendez-vous a venir</h3>
<table border="1" cellpadding="4">
    <tr><th>Date</th><th>Medecin</th><th>Motif</th><th>Statut</th></tr>
    <%
        if (futureRdvs != null) {
            for (RendezVous r : futureRdvs) {
                String mnom = r.getMedecin() != null ? r.getMedecin().getNom() : "";
    %>
    <tr>
        <td><%= r.getDateRendezVous() %></td>
        <td><%= mnom %></td>
        <td><%= r.getMotif() != null ? r.getMotif() : "" %></td>
        <td><%= r.getStatut() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
<% if (futureRdvs == null || futureRdvs.isEmpty()) { %>
<p><em>Aucun.</em></p>
<% } %>

<h3>Rendez-vous passes</h3>
<table border="1" cellpadding="4">
    <tr><th>Date</th><th>Medecin</th><th>Motif</th><th>Statut</th></tr>
    <%
        if (pastRdvs != null) {
            for (RendezVous r : pastRdvs) {
                String mnom = r.getMedecin() != null ? r.getMedecin().getNom() : "";
    %>
    <tr>
        <td><%= r.getDateRendezVous() %></td>
        <td><%= mnom %></td>
        <td><%= r.getMotif() != null ? r.getMotif() : "" %></td>
        <td><%= r.getStatut() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
<% if (pastRdvs == null || pastRdvs.isEmpty()) { %>
<p><em>Aucun.</em></p>
<% } %>

<p><a href="logout">Logout</a></p>

</body>
</html>
