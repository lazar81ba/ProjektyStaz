<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<ul>

		
		<li><a href="/RestClient/getByModelJSP">getByModel</a></li>
		<li><a href="/RestClient/getByIdJSP">getById</a></li>
		<sec:authorize access="hasRole('ADMIN')">
		<li><a href="/RestClient/addJSP">Add</a></li>
		<li><a href="/RestClient/updateJSP">update</a></li>
		<li><a href="/RestClient/deleteJSP">delete</a></li>
		</sec:authorize>
		<li><a href="/RestClient/download">Export database to XML</a></li>
		<li><a href="/RestClient/downloadJSON">Export database to JSON</a></li>
		
		<li><a href="/RestClient/upload">upload</a></li>
		
		<li><a href="/RestClient/logout">Wyloguj sie</a>
		</li>
		
	</ul>
	
			<br/>
			<br/>
			<br/>
		<p>SOAP:</p>
		<ul>
			<li><a href="/RestClient/getByModelSoapJSP">getByModel</a></li>
			<li><a href="/RestClient/getByIdSoapJSP">getById</a></li>
			<sec:authorize access="hasRole('ADMIN')">
			<li><a href="/RestClient/addSoapJSP">Add</a></li>
			<li><a href="/RestClient/updateSoapJSP">update</a></li>
			<li><a href="/RestClient/deleteSoapJSP">delete</a></li>
			</sec:authorize>
		</ul>
</body>
</html>