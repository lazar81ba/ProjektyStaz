<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Insert title here</title>
</head>
<body>

<header>Upload</header>

<form method="POST" id="fileUpload" action="/RestClient/upload" enctype="multipart/form-data">
    <input type="file" name="file" /><br/>
    
    <input type="radio" value="json"  name="fileType"  onclick="document.getElementById('fileUpload').action='/RestClient/uploadJSON';"/><label>json</label>
    <input type="radio" value="xml"  name="fileType"  checked="checked" onclick="document.getElementById('fileUpload').action='/RestClient/upload';"/><label >xml</label> 
    <br/>       
    <input type="submit" value="Submit" />
</form>


</body>
</html>