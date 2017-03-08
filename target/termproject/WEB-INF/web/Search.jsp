<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Search</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>

    <style type="text/css">
        .container {
            padding: 40px;
        }
        .search-box {
            display: inline-block;
            width: 100%;
            border-radius: 3px;
            padding: 4px 55px 4px 15px;
            position: relative;
            background: #fff;
            border: 1px solid #ddd;
            -webkit-transition: all 200ms ease-in-out;
            -moz-transition: all 200ms ease-in-out;
            transition: all 200ms ease-in-out;
        }
        .search-box.hovered, .search-box:hover, .search-box:active {
            border: 1px solid #aaa;
        }
        .search-box input[type=text] {
            border: none;
            box-shadow: none;
            display: inline-block;
            padding: 0;
            background: transparent;
        }
        .search-box input[type=text]:hover, .search-box input[type=text]:focus, .search-box input[type=text]:active {
            box-shadow: none;
        }
        .search-box .search-btn {
            position: absolute;
            right: 2px;
            top: 2px;
            color: #aaa;
            border-radius: 3px;
            font-size: 21px;
            padding: 5px 10px 1px;
            -webkit-transition: all 200ms ease-in-out;
            -moz-transition: all 200ms ease-in-out;
            transition: all 200ms ease-in-out;
        }
        .search-box .search-btn:hover {
            color: #fff;
            background-color: #8FBE00;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp" />
<div class='container'>
    <div class='row'>
        <div class='col-md-12'>
            <h1>Book Search</h1>
            <p class='lead'>Search the books by bookname,created by or updated by. </p>
        </div>
    </div>
    <div class='row'>
        <div class='col-md-12'>
            <div class='search-box'>
                <form class='search-form' id="searchform" action="/search/forResults" method="post">
                    <input class='form-control' name="booktitle" placeholder='The name of book you want to search(use the book name for search and the below selection will be ignored).' type='text'>
                    <button class='btn btn-link search-btn' type="submit">
                        <i class='glyphicon glyphicon-search'></i>
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class='row' >
        <div class="col-md-6" style="margin: 25px">
            <select name="Action" class="selectpicker" form="searchform" >
                <option value="create">Created</option>
                <option value="update">Updated</option>
            </select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;By &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="Librarian" class="selectpicker" form="searchform" >
                <c:forEach items="${Librarian}" var="librarian">
                    <option value=${librarian.uid}>${librarian.email}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-6">
        </div>
    </div>

    <div class="row">
        <div class='col-md-12'>
            <c:if test="${!empty SearchType}"><div class="panel panel-primary" style="color:royalblue"><H4>The results for search by: ${SearchType}</H4></div></c:if>
            <c:if test="${!empty booklist}">
                <table class="table table-bordered table-striped">
                    <tr>
                        <th>Book Name</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Publisher</th>
                        <th>Modification</th>
                    </tr>

                    <c:forEach items="${booklist}" var="book">
                        <tr>
                            <td><a href="/book/${book.getBid()}" >${book.title}</a></td>
                            <td>${book.author}</td>
                            <td>${book.publisher}</td>
                            <td>${book.status}</td>
                            <td>
                                <c:if test="${sessionScope.get('edit')}">
                                <a href="/book/${book.getBid()}/update" type="button" class="btn btn-md btn-info">Update</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="/book/${book.getBid()}/delete" type="button" class="btn btn-md btn-danger">Delete</a>
                                </c:if>
                                <c:if test="${sessionScope.get('borrow')}">
                                    <a href="/book/${book.getBid()}" class="btn btn-info" role="button">Detail</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>