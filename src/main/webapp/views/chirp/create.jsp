<%--
 * create.jsp
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

<security:authorize access="hasRole('USER')">
<form:form action="${requestURI}" modelAttribute="chirp">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="user"/>
	
	<acme:textbox code="chirp.postMoment" path="postMoment" placeholder="dd/MM/yyyy HH:mm"/>
	<br />
	
	<acme:textbox code="chirp.title" path="title"/>
	<br />

	<acme:textbox code="chirp.description" path="description"/>
	<br />
	
	<!-- Buttons -->
	<acme:submit name="save" code="chirp.save"/>
	<acme:cancel url="/welcome/index.do" code="chirp.cancel"/>
</form:form>
</security:authorize>