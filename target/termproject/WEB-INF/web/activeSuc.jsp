<%--
  Created by IntelliJ IDEA.
  User: ryanf
  Date: 12/6/2016
  Time: 12:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Active</title>
    <!-- bootstrap css -->
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
</head>
<body>
<jsp:include page="header.jsp" />
    <p>Dear user,</p>
    <p>You have successful active your account.</p>
    <p>Go to home page start use:</p>
    <a href="/books" class="btn btn-success">Start</a>
</body>
</html>
