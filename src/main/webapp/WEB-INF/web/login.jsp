<%--
  Created by IntelliJ IDEA.
  User: ryanf
  Date: 12/1/2016
  Time: 10:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- spring -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login</title>
    <!-- bootstrap css -->
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
<style>
    .col-center-block {
        float: none;
        display: table;
        margin-left: auto;
        margin-right: auto;
    }
</style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="panel-body">
    <div class="row">
        <div class="col-lg-3 col-center-block">
        ${err}

        <sf:form class="form"  method="POST" action="/login">
            EMAIL: <input type="email" class="form-control" placeholder="Email" name="email" id="email" />

            PASSWORD: <input type="password" class="form-control" placeholder="Password" name="password" id="password" />

            <input class="btn btn-success" type="submit" value="submit" path="submit" />
            <a class="btn btn-success" href="/signup">Signup</a>

        </sf:form>
        </div>
    </div>
</div>
</body>
</html>
