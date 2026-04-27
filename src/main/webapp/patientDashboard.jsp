<%@ page import="com.example.exam.model.Patient" %>
<%
    Patient p = (Patient) session.getAttribute("patient");
    if (p == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Patient Dashboard</title>
</head>

<body>

<h2>👋 Welcome, <%= p.getNom() %></h2>

<h3>📅 Your Appointments</h3>

<a href="patientsRdv">View My RDVs</a><br>
<a href="createRdv.jsp">Book Appointment</a><br>
<a href="logout">Logout</a>

</body>
</html>