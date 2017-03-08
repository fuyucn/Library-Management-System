<%--
  Created by IntelliJ IDEA.
  User: fu
  Date: 11/30/16
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- spring -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Register</title>
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
</head>
<body>
<jsp:include page="header.jsp" />

    <div class="container">
        <div>
            ${err}
        </div>
        <sf:form method="POST" class="form-control" commandName="user" action="/signup">

        <div calss="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" placeholder="Email" name="email" id="email"/>
        </div>
        <div calss="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" placeholder="Password" name="password" id="password"/>
        </div>
        <div calss="form-group">
            <label for="id">University id:</label>
            <input type="text" class="form-control" placeholder="University ID: 6 digits" maxlength="6" name="id" id="id" />
        </div>
        <div calss="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control"  placeholder="First Name" name="firstName" id="firstName"/>
        </div>
        <div calss="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control"  placeholder="Last Name" name="lastName" id="lastName"/>
        </div>
        <div calss="form-group">
            <input class="btn btn-success" type="submit" value="submit" />
            <a class="btn btn-success" href="/login">login</a>
        </div>
        </sf:form>
    </div>



<script>
    jQuery(function ($) {
        $('id').mask("999999");
    })
</script>
</body>
</html>
