<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- For JSTL 3.x --%>
<%@ taglib uri="jakarta.tags.core" 	prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" 	prefix="fmt" %>

<%-- For JSTL 1.x --%>
<%-- @ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" --%>
<%-- @ taglib uri="http://java.sun.com/jsp/jstl/fmt" 	prefix="fmt" --%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Security Home</title>
</head>

<body>
	<h3>${pageContext.request.requestURI}</h3>
	<hr>
	
	<h3>1. SPRING_SECURITY_CONTEXT</h3>
	<p>1-1. SecurityContextImpl : ${SPRING_SECURITY_CONTEXT}</p>
	<p>1-2. UsernamePasswordAuthenticationToken : ${SPRING_SECURITY_CONTEXT.authentication}</p>
	<p>1-3. Principal : ${SPRING_SECURITY_CONTEXT.authentication.principal}</p>
	<p>1-4. Credentials : ${SPRING_SECURITY_CONTEXT.authentication.credentials}</p>
	<p>1-5. Authenticated : ${SPRING_SECURITY_CONTEXT.authentication.authenticated}</p>
	<p>1-6. Details : ${SPRING_SECURITY_CONTEXT.authentication.details}</p>
	<p>1-7. Authorities : ${SPRING_SECURITY_CONTEXT.authentication.authorities}</p>
	<hr>
	
	<h3>2. _csrf</h3>
	<p>${_csrf}</p>
	<hr>

</body>
</html>

