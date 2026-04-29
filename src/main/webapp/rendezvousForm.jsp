<%@ page contentType="text/html;charset=UTF-8" %>
<%
    java.util.List<com.example.exam.model.Patient> patients =
            (java.util.List<com.example.exam.model.Patient>) request.getAttribute("patients");
    java.util.List<com.example.exam.model.Medecin> medecins =
            (java.util.List<com.example.exam.model.Medecin>) request.getAttribute("medecins");
%>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h2>Nouveau rendez-vous</h2>

<form action="<%= request.getContextPath() %>/rendezvous" method="post">
    <input type="hidden" name="action" value="create"/>

    Patient:
    <select name="patientId" required>
        <%
            if (patients != null) {
                for (com.example.exam.model.Patient p : patients) {
        %>
        <option value="<%= p.getId() %>"><%= p.getNom() %> <%= p.getPrenom() %> (<%= p.getEmail() %>)</option>
        <%
                }
            }
        %>
    </select><br><br>

    Medecin:
    <select name="medecinId" required>
        <%
            if (medecins != null) {
                for (com.example.exam.model.Medecin m : medecins) {
        %>
        <option value="<%= m.getId() %>">Dr <%= m.getNom() %> - <%= m.getSpecialite() %></option>
        <%
                }
            }
        %>
    </select><br><br>

    Date et heure: <input type="datetime-local" name="dateRendezVous" required/><br><br>
    Motif: <input type="text" name="motif" size="40"/><br><br>

    <button type="submit">Creer</button>
</form>

<p><a href="<%= request.getContextPath() %>/rendezvous?action=listToday">Annuler</a></p>

</body>
</html>
