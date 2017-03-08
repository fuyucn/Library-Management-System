<%--
  Created by IntelliJ IDEA.
  User: ryanf
  Date: 12/5/2016
  Time: 8:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- spring -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>active</title>
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>

</head>
<body>
<jsp:include page="header.jsp" />
    <sf:form class="form-control" method="POST" action="/active">
        ${err}
        UserID: <p>${ uid }</p>
        Active Code: <input type="text" class="form-control" placeholder="Active Code:" maxlength="5" name="code" id="code" />
        <input class="btn btn-success" type="submit" name="submit" id = "submit" value="submit" />
    </sf:form>
</body>
</html>
