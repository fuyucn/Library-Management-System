<%--
  Created by IntelliJ IDEA.
  User: fu
  Date: 12/7/16
  Time: 02:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Book</title>
    <link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
    <!-- jquery -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
    <!-- jquery masked -->
    <script href="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>

</head>
<body>
<jsp:include page="header.jsp" />
    <div class="container">
        <h1>${book.getTitle()}</h1>
        <dl>
            <dd>Author ${book.getAuthor()} | Year: ${book.getYear()} | lociation: ${book.getLocation()}</dd>
            <p class="bg-warning">Keyword: ${book.getKeywords()}</p>
            <p class="bg-warning">Call Number: ${book.getCallNumber()}</p>
            <dt><p class="bg-success">publisher: ${book.getPublisher()}</p></dt>
        </dl>


    <% if(session!=null){
        if(session.getAttribute("edit")!=null && (boolean)session.getAttribute("edit")){ %>

        <a href="/book/${book.getBid()}/update" class="btn btn-info" role="button">update</a>
        <a href="/book/${book.getBid()}/delete" class="btn btn-danger" role="button">delete</a>
        <%  }
        if(session.getAttribute("borrow")!=null && (boolean)session.getAttribute("borrow")) {%>
        <c:choose>
            <c:when test="${avaliable}">
                <a href="/book/${book.getBid()}/borrow" class="btn btn-info" role="button">Borrow</a>
            </c:when>
            <c:otherwise>
                <a href="/waitlist/add/${book.getBid()}" class="btn btn-info" role="button">WaitList</a>
            </c:otherwise>
        </c:choose>
        <%  }
    }else{ %>

        <% } %>
    </div>
</body>
</html>
