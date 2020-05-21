<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sensitive Data Redactor</title>
</head>
<body>
<br>
<h5>Please Enter the text which you would like to redact the sensitive data from:</h5>
<br>
<div style="align:middle">
    <form action="dataredactor" method="post">
      
          <textarea id="inputText" name="inputText" style="width:500px;height:250px"></textarea><br>
			<br>Redaction Options:<br>
		  <input type="checkbox" name= "person" value="person">Person Names &nbsp;&nbsp;&nbsp;
		  <input type="checkbox" name="location" value="location">Location Names &nbsp;&nbsp;&nbsp;
		  <input type="checkbox" name="orgs" value="orgs">Organization Names &nbsp;&nbsp;&nbsp;
		  <input type="checkbox" name="date" value="date">Dates &nbsp;&nbsp;&nbsp;
		  <input type="checkbox" name="time" value="time">Times &nbsp;&nbsp;&nbsp;
			<br><br>
          <input type="submit" value="Submit">
        
    </form>
</div>

${results}

${redactionOptions}
<br> <br>
${inputString}
<br> <br>
${redactedString}

</body>
</html>