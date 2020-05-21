<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sentiment Analyser</title>
</head>
<body>
<br>
<h5>Please Enter the text which you would like to perform sentiment analysis on:</h5>
<br>
<div style="align:middle">
    <form action="sentimentanalyser" method="post">
      
          <textarea id="inputText" name="inputText" style="width:500px;height:100px"></textarea><br>
			<br>
          <input type="submit" value="Submit">
        
    </form>
</div>

<br>
<h5>${results}</h5>


</html>