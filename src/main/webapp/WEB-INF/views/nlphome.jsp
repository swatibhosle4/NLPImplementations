<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Natural Language Processing: Home</title>
</head>
<body>
<br>
<h5>Please select your operation :</h5>
<br>
<div style="align:middle">
    <form action="dataredactor" method="get">
          <input type="submit" value="Data Redaction">
    </form>
    &nbsp;&nbsp;&nbsp;
    <form action="sentimentanalyser" method="get">
          <input type="submit" value="Sentiment Analysis">
    </form>
</div>
</html>