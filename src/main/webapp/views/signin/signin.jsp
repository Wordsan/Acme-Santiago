<%--
 * edit.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="security/user/signin.do" modelAttribute="signinForm">

	<acme:textbox code="signin.username" path="username"/>
	<br />
	
	<acme:password code="signin.password" path="password"/>
	<br />
	
	<acme:password code="signin.confirmPassword" path="confirmPassword"/>
	<br />
	
	<acme:textbox code="signin.name" path="name"/>
	<br />
	
	<acme:textbox code="signin.surname" path="surname"/>
	<br />
	
	<acme:textbox code="signin.picture" path="picture"/>
	<br />
	
	<acme:textbox code="signin.postalAddress" path="postalAddress"/>
	<br />

	<acme:textbox code="signin.phone" path="phoneNumber"/>
	<br />
	
	<acme:textbox code="signin.email" path="emailAddress"/>
	<br />
	
	<acme:checkbox code="signin.conditionsAccepted" path="conditionsAccepted"/>
	<br />
	

	<!-- Buttons -->

	<acme:cancel url="/" code="common.action.cancel"/>
		
	<acme:submit name="signin" code="common.action.signin" />
			
</form:form>