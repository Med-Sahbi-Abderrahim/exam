<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Long rdvId = (Long) request.getAttribute("rdvId");
%>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<h2>Reporter un rendez-vous</h2>

<% if (rdvId == null) { %>
<p>ID manquant.</p>
<% } else { %>
<form action="<%= request.getContextPath() %>/rendezvous" method="post">
    <input type="hidden" name="action" value="reschedule"/>
    <input type="hidden" name="id" value="<%= rdvId %>"/>
    Nouvelle date et heure: <input type="datetime-local" name="newDate" required/><br><br>
    <button type="submit">Enregistrer</button>
</form>
<% } %>

<p><a href="<%= request.getContextPath() %>/rendezvous?action=listAll">Retour liste</a></p>

</body>
</html>
