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

<security:authorize access="hasRole('ADMIN')">
<form:form action="${requestURI}" modelAttribute="configurationSystem">

	<form:hidden path="id"/>
	
	<acme:textbox code="configurationSystem.tabooWords" path="tabooWords"/>
	<br />
	
	<!-- Buttons -->
	<acme:cancel url="/welcome/index.do" code="configurationSystem.cancel"/>
	<acme:submit name="save" code="configurationSystem.save"/>
</form:form>
</security:authorize>