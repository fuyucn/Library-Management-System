<%--
  Created by IntelliJ IDEA.
  User: ryanf
  Date: 12/5/2016
  Time: 8:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>

</head>
<body>
    <jsp:include page="header.jsp" />

    <% if(session.getAttribute("borrow")!=null && (boolean)session.getAttribute("borrow")) {%>
    <p>Dear user: ${email}</p>
    <p>Login succ</p>

    <p>You have own: $${fee}</p>
    <%  } %>
    <% if(session.getAttribute("edit")!=null && (boolean)session.getAttribute("edit")) {%>
    <div class="row">
        <div class="col-md-6"></div>
        <div class="col-md-6">
           CurrentTime: <%= new java.util.Date()%>
           <!-- <sf:form class="form"  method="POST" action="/newtime">
                Time: <input type="text" class="form-control" placeholder="time" name="time" id="time" />
                <input class="btn btn-success" type="submit" value="submit" path="submit" />
            </sf:form> -->
        </div>
    </div>
    <p>Dear Librian: ${email}</p>
    <p>Login succ</p>

    <%  } %>
</body>
</html>
