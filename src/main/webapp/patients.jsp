<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h2>Liste des Patients</h2>

<form action="patients" method="get">
    <input type="hidden" name="action" value="search"/>
    <input type="text" name="keyword" placeholder="Rechercher..."/>
    <button type="submit">Search</button>
</form>

<a href="form.jsp">Ajouter Patient</a>

<table border="1">
    <tr>
        <th>Nom</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>

    <%
        java.util.List<com.example.exam.model.Patient> patients =
                (java.util.List<com.example.exam.model.Patient>) request.getAttribute("patients");

        for (com.example.exam.model.Patient p : patients) {
    %>

    <tr>
        <td><%= p.getNom() %></td>
        <td><%= p.getEmail() %></td>
        <td>
            <a href="patients?action=edit&id=<%= p.getId() %>">Edit</a>
            <a href="patients?action=delete&id=<%= p.getId() %>">Delete</a>
        </td>
    </tr>

    <% } %>

</table>

</body>
</html>