<%@ page import="com.example.exam.model.Medecin" %>
<%
  Medecin m = (Medecin) session.getAttribute("medecin");
  if (m == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>

<html>
<head>
  <title>Médecin Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<h2>Dr. <%= m.getNom() %></h2>

<h3>Management</h3>

<a href="patients">Patients List</a><br>
<a href="medecins">Medecins List</a><br>
<a href="<%= request.getContextPath() %>/rendezvous?action=listToday">Rendez-vous</a><br>
<a href="logout">Logout</a>

</body>
</html>