<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Book Create Form</title>
	<link href="<c:url value='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />" rel="stylesheet"></link>
	<!-- jquery -->
	<script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js' />"></script>
	<!-- jquery masked -->
    <script src="<c:url value='http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js' />"></script>
	<script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.js' />"></script>
    <script>
        function do_search(){
            var url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + document.getElementById('isbn').value;;

            var response = UrlFetchApp.fetch(url);
            var results = JSON.parse(response);
            if (results.totalItems) {
                // There'll be only 1 book per ISBN
                var book = results.items[0];
                var title = (book["volumeInfo"]["title"]);
                var authors = (book["volumeInfo"]["authors"]);
                var publisher = (book["volumeInfo"]["publisher"]);
                var publishedDate = (book["volumeInfo"]["publishedDate"]);
                document.getElementById('title').value = title;
                document.getElementById('authors').value = authors;
                document.getElementById('publisher').value = publishedDate;
                document.getElementById('year').value = publishedDate;
                document.getElementById('title').value = title;
            }
        }
    </script>

</head>

<body>
<jsp:include page="header.jsp" />
<div class="generic-container" >

	<div class="well lead">Book Create Form</div>
	<!--<input type="number" path="isbn" name="isbn"  id="isbn"/>
	<button onclick="do_search();" id="submit" name="submit">search</button>-->
	<form method="POST" commandName="book" modelAttribute="book" action= "${link}" class="form-horizontal" style="padding:10px 20px 10px 20px ">

		<div class="row">

			<input type="hidden" path="bid" name="bid"  id="bid" value="${book.getBid()}"/>
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="title">Book
					Title</label>
				<div class="col-md-7">
					<input type="text" path="title" name="title"  id="title"
						   class="form-control input-sm" value="${book.getTitle()}"/>
					<div class="has-error">
						<errors  path="title" name="title"  class="help-inline" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="author">Author</label>
				<div class="col-md-7">
					<input type="text" name="author" path="author" id="author" maxlength="40"
						   class="form-control input-sm" value="${book.getAuthor()}"/>
					<div class="has-error">
						<errors name="author" path="author" class="help-inline" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="callNumber">Call number</label>
				<div class="col-md-7">
					<input type="text" name="callNumber" path="callNumber" id="callNumber" maxlength="40"
						   class="form-control input-sm" value="${book.getCallNumber()}"/>
					<div class="has-error">
						<errors name="callNumber" path="callNumber" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="publisher">Publisher</label>
				<div class="col-md-7">
					<input type="text" name="publisher" path="publisher" id="publisher" maxlength="40"
						   class="form-control input-sm" value="${book.getPublisher()}" />
					<div class="has-error">
						<errors name="publisher" path="publisher" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="year">Year</label>
				<div class="col-md-7">
					<input type="text" name="year" path="year" id="year" maxlength="4"
						   class="form-control input-sm" value="${book.getYear()}" />
					<div class="has-error">
						<errors name="year" path="year" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="location">Location</label>
				<div class="col-md-7">
					<input type="text" name="location" path="location" id="location" maxlength="40"
						   class="form-control input-sm"  value="${book.getLocation()}"  />
					<div class="has-error">
						<errors name="location" path="location" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="number">number</label>
				<div class="col-md-7">
					<input type="text" name="number" path="number" id="number" maxlength="10"
						   class="form-control input-sm" value="${book.getNumber()}"  />
					<div class="has-error">
						<errors name="number" path="number" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="status">status</label>
				<div class="col-md-7">
					<input type="text" name="status" path="status" id="status" maxlength="40"
						   class="form-control input-sm" value="${book.getStatus()}"  />
					<div class="has-error">
						<errors name="status" path="status" class="help-inline" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="keywords">keywords</label>
				<div class="col-md-7">
					<input type="text" name="keywords" path="keywords" id="keywords" maxlength="40"
						   class="form-control input-sm" value="${book.getKeywords()}"  />
					<div class="has-error">
						<errors name="keywords" path="keywords" class="help-inline" />
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-actions floatRight">
				<c:choose>
					<c:when test="${edit}">
						<input type="submit" value="Update"
							   class="btn btn-primary btn-sm" /> or <a
							href="<c:url value='/book/${book.getBid()}' />">Cancel</a>
					</c:when>
					<c:otherwise>
						<input type="submit" value="Add"
							   class="btn btn-primary btn-sm" /> or <a
							href="<c:url value='/books' />">Cancel</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form>

	<script type="text/javascript">
		jQuery(function ($) {
			$('#year').mask("9999");
		})
	</script>



</div>
</body>
</html>