<%@ page contentType="text/html;charset=UTF-8" %>
<%
    com.example.exam.model.Medecin medecin =
            (com.example.exam.model.Medecin) request.getAttribute("medecin");
    java.util.List<com.example.exam.model.Patient> doctorPatients =
            (java.util.List<com.example.exam.model.Patient>) request.getAttribute("doctorPatients");
%>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h2>Patients du medecin <%= medecin != null ? medecin.getNom() + " " + medecin.getPrenom() : "" %></h2>

<p><a href="medecins">Retour liste medecins</a></p>

<table border="1">
    <tr>
        <th>Nom</th>
        <th>Prenom</th>
        <th>Email</th>
        <th>Telephone</th>
    </tr>
    <%
        if (doctorPatients != null) {
            for (com.example.exam.model.Patient p : doctorPatients) {
    %>
    <tr>
        <td><%= p.getNom() %></td>
        <td><%= p.getPrenom() %></td>
        <td><%= p.getEmail() %></td>
        <td><%= p.getTelephone() != null ? p.getTelephone() : "" %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<% if (doctorPatients == null || doctorPatients.isEmpty()) { %>
<p><em>Aucun patient (aucun rendez-vous avec ce medecin).</em></p>
<% } %>

</body>
</html>
