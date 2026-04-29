<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<h2>🔐 Login</h2>

<form action="login" method="post">

    Email: <input type="text" name="email"><br><br>
    Password: <input type="password" name="password"><br><br>

    <label>Role:</label>
    <select name="role">
        <option value="patient">Patient</option>
        <option value="medecin">Médecin</option>
    </select>

    <br><br>
    <button type="submit">Login</button>

</form>

</body>
</html>