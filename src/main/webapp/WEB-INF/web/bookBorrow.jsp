<%--
  Created by IntelliJ IDEA.
  User: fu
  Date: 12/7/16
  Time: 05:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Borrow Carts</title>
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
    <style>
        .label-as-badge {
            border-radius: 1em;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
	${err}
    <h1>Book List:</h1>
    <ul class="list-group">
        <c:forEach items="${bbs}" var="book">
            <li class="list-group-item">
                <div class="container">
                        ${book}
                </div>
            </li>
            <hr>
        </c:forEach>
    </ul>
</div>

</body>
</html>
