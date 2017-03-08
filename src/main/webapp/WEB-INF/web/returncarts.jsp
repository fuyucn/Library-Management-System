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
    <title>Return Carts</title>
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
    <h1>Return Carts List:</h1>

    ${err}

    <a href="/carts/return/submit" class="btn btn-success" role="button">Submit</a>
    <ul class="list-group">
        <c:forEach items="${allReturns}" var="book">
            <li class="list-group-item">
                <h5>
                    <div class="row">
                        <div class="col-md-1"><span class="label label-info label-as-badge">Title </span></div>
                        <div class="col-md-5"><p style="display:inline">${book.value.getTitle()}</p></div>
                        <div class="col-md-4"><span class="label label-info label-as-badge">Year: </span> ${book.value.getYear()}</div>
                        <div class="col-md-2"><a href="/book/${book.value.getBid()}" class="btn btn-info" role="button">Detail</a></div>
                    </div>
                    <div class="row">
                        <div class="col-md-12"><small>${book.value.getAuthor()}</small></div>
                    </div>
                </h5>
                <div class="row">
                    <div class="col-md-2"><span class="label label-info label-as-badge">Keywords: </span></div>
                    <div class="col-md-10"><p style="display:inline">${book.value.getKeywords()}</p></div>
                </div>
                <div class="row">
                    <div class="col-md-6"><span class="label label-info label-as-badge">Publish: </span><p style="display:inline">${book.value.getPublisher()}</p></div>
                    <div class="col-md-6"><span class="label label-info label-as-badge">callNumber: </span><p style="display:inline">${book.value.getCallNumber()}</p></div>
                </div>
                <% if(session.getAttribute("carts")!=null){ %>
                <a href="/user/borrow/return/${book.key}/delete" class="btn btn-danger" role="button">Delete</a>
                <%  } %>
            </li>
            <hr>
        </c:forEach>
    </ul>
</div>

</body>
</html>
