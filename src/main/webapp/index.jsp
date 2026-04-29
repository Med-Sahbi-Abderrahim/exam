<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Hospital System - Home</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial;
            text-align: center;
            margin-top: 80px;
        }

        .box {
            border: 1px solid #ccc;
            width: 400px;
            margin: auto;
            padding: 20px;
            border-radius: 10px;
        }

        a {
            display: block;
            margin: 15px;
            padding: 10px;
            text-decoration: none;
            background: #4CAF50;
            color: white;
            border-radius: 5px;
        }

        a:hover {
            background: #45a049;
        }

        .role {
            margin-top: 20px;
        }
    </style>
</head>

<body>

<div class="box">

    <h1>🏥 Hospital Management System</h1>
    <p>Welcome to the medical appointment platform</p>

    <!-- 🔹 Navigation -->
    <a href="login.jsp">🔐 Login</a>
    <a href="SignupPatient.jsp">🧑 Signup as Patient</a>
    <a href="SignupMedecin.jsp">👨‍⚕️ Signup as Medecin</a>

    <hr>

    <!-- 🔹 Role info (optional UX improvement) -->
    <div class="role">
        <h3>Choose your role</h3>

        <p>Patients can book and manage appointments</p>
        <p>Doctors can manage consultations</p>
    </div>

</div>

</body>
</html>