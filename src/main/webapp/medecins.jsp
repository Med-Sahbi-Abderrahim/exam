<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<body>

<h2>Liste des Medecins</h2>

<% if ("delete".equals(request.getParameter("error"))) { %>
<p style="color:red;">Impossible de supprimer : ce medecin a des rendez-vous.</p>
<% } %>

<form action="medecins" method="get">
    <input type="hidden" name="action" value="search"/>
    <input type="text" name="spec" placeholder="Rechercher par specialite..."/>
    <button type="submit">Search</button>
</form>

<h3>Ajouter un Medecin</h3>
<form action="medecins" method="post">
    Nom: <input type="text" name="nom"/><br>
    Prenom: <input type="text" name="prenom"/><br>
    Specialite: <input type="text" name="specialite"/><br>
    Email: <input type="email" name="email"/><br>
    <button type="submit">Ajouter</button>
</form>

<table border="1">
    <tr>
        <th>Nom</th>
        <th>Prenom</th>
        <th>Specialite</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>

    <%
        java.util.List<com.example.exam.model.Medecin> medecins =
                (java.util.List<com.example.exam.model.Medecin>) request.getAttribute("medecins");
        if (medecins != null) {
            for (com.example.exam.model.Medecin m : medecins) {
    %>
    <tr>
        <td><%= m.getNom() %></td>
        <td><%= m.getPrenom() %></td>
        <td><%= m.getSpecialite() %></td>
        <td><%= m.getEmail() %></td>
        <td>
            <a href="medecins?action=patients&amp;id=<%= m.getId() %>">Patients</a>
            |
            <a href="medecins?action=delete&amp;id=<%= m.getId() %>"
               onclick="return confirm('Supprimer ce medecin ? (refuse si RDV existants)');">Supprimer</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<p><a href="medecinDashboard.jsp">Retour dashboard</a></p>

</body>
</html>
