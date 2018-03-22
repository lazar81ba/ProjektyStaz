<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form action="/RestClient/getByModelJSP" method="POST" modelAttribute="parameter">
	<form:label path="parameter">Model</form:label>
		<br />
		<form:input path="parameter" placeholder="Model" />
		<br />
		<input type="submit" value="Submit"/>
		
</form:form>
<c:forEach  items="${list}" var="phone">
	<ul>
		<li>
                <c:out value="${phone.getModel()}" />
                <c:out value="${phone.getProducer()}" />
                <c:out value="${phone.getSystem()}" />
                <c:out value="${phone.getSerial_number()}" />
         </li>
     </ul>
	</c:forEach>

	



</body>
</html>