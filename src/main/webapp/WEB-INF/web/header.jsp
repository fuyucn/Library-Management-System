<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>

<nav class="navbar navbar-default bg-faded">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/loginsuccess">LibSys</a>
        </div>
    <ul class="nav navbar-nav">

        <li class="nav-item">
            <a class="nav-link" href="/books">Book list</a>
        </li>

        <% if(session!=null){
            if(session.getAttribute("edit")!=null && (boolean)session.getAttribute("edit")){ %>
        <li class="nav-item">
            <a href="/search" class="btn" role="button">search book</a>
        </li>
        <li class="nav-item">
            <a href="http://localhost:8080/book/create" class="btn btn-block" role="button">create book</a>
        </li>
        <%  }
            if(session.getAttribute("borrow")!=null && (boolean)session.getAttribute("borrow")) {%>
        <li class="nav-item">
            <a href="/search" class="btn" role="button btn-block">search book</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/books/carts">Borrow Carts</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/return">Return Carts</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/borrow">User Borrow Histroy</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/waitlist">Waitlist</a>
        </li>
        <%  }
        } %>
        <% if(session.getAttribute("uid")!=null){ %>
        <li class="nav-item">
            <a class="nav-link" href="/logout">Logout</a>
        </li>
        <% }else { %>
        <li class="nav-item">
            <a class="nav-link" href="/login">Login</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/signup">Signup</a>
        </li>
        <% } %>
    </ul>
    </div>
</nav>