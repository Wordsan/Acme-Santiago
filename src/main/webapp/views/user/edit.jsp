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


<form:form action="${ requestURI }" modelAttribute="configuration">

	<form:hidden path="id" />
	

	<acme:textbox code="configuration.name" path="name" readonly="true"/>
	<br />
	
	<acme:textbox code="configuration.type" path="type" readonly="true"/>
	<br />
	
	<acme:textarea code="configuration.validations" path="validations" readonly="true"/>
	<br />
	
	<acme:textarea code="configuration.value" path="value"/>
	<br />
	

	<!-- Buttons -->

	<acme:cancel url="configuration/administrator/list.do" code="common.action.cancel"/>
		
	<acme:submit name="save" code="common.action.save" />
			
</form:form>