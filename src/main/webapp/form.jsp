<%@ page contentType="text/html;charset=UTF-8" %>
<%
    com.example.exam.model.Patient p =
            (com.example.exam.model.Patient) request.getAttribute("patient");
%>

<html>
<body>

<h2>Patient Form</h2>

<form action="patients" method="post">

    <input type="hidden" name="id" value="<%= (p != null ? p.getId() : "") %>"/>

    Nom: <input type="text" name="nom" value="<%= (p != null ? p.getNom() : "") %>"/><br>
    Prenom: <input type="text" name="prenom" value="<%= (p != null ? p.getPrenom() : "") %>"/><br>
    Email: <input type="email" name="email" value="<%= (p != null ? p.getEmail() : "") %>"/><br>
    Telephone: <input type="text" name="telephone" value="<%= (p != null ? p.getTelephone() : "") %>"/><br>
    Date Naissance: <input type="date" name="dateNaissance"
                           value="<%= (p != null && p.getDateNaissance() != null ? p.getDateNaissance().toString() : "") %>"/><br>

    <button type="submit">Save</button>
</form>

</body>
</html>