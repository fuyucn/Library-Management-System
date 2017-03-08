    <%--
  Created by IntelliJ IDEA.
  User: ryanf
  Date: 12/5/2016
  Time: 8:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Success</title>
    <!-- bootstrap css -->
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="alert alert-success">

        <strong>Success!</strong>
        <p>Dear User: ${email},</p>
        ${message}.

        Back to <a class="btn btn-success" href="/login">Login</a> OR <a class="btn btn-success" href="/">home</a>
    </div>
</body>
</html>
