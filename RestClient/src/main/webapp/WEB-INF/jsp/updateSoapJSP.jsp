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
<c:if test = "${parameter.getParameter() == null}">
	<form:form action="/RestClient/updateSoapJSP" method="POST" modelAttribute="parameter">
		<form:label path="parameter">ID</form:label>
			<br />
			<form:input path="parameter" placeholder="Id" type="number"/>
			<br />
			<input type="submit" value="Submit"/>
	</form:form>
</c:if>

<c:if test = "${phone != null}">
	 <c:out value="${phone.getModel()}" />
     <c:out value="${phone.getProducer()}" />
     <c:out value="${phone.getSystem()}" />
     <c:out value="${phone.getSerial_number()}" />

	<form:form  action="/RestClient/updateProxySoapJSP" method="POST" modelAttribute="proxy">
	<form:input path="id" value="${phone.getId()}" type="hidden"/>
		<form:label path="model">Model</form:label>
			<br />
			<form:input path="model"/>
			<br />
		<form:label path="producer">Producer</form:label>
			<br />
			<form:input path="producer"/>
			<br />
		<form:label path="system">System</form:label>
			<br />
			<form:input path="system"/>
			<br />
		<form:label path="serial_number">Serial Number</form:label>
			<br />
			<form:input path="serial_number"/>
			<br/>
		<input type="submit" value="Submit"/>
	</form:form>
</c:if>
</body>
</html>