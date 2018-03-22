<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:forEach items="${listModel}" var="model">
		<c:out value="${model.getString()}" />
		<c:out value="${model.getLongNumber()}" />
		<c:out value="${model.getStrings()}" />
		<c:forEach items="${model.getSecondModels()}" var="secondModel">
			<ul>
				<li><c:out value="${secondModel.getFirstString()}" /> </li>
				<li><c:out value="${secondModel.getSecondString()}" /></li>
			</ul>
		</c:forEach>
	</c:forEach>





</body>
</html>