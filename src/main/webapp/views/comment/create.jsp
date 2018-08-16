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
<form:form action="${requestURI}" modelAttribute="comment">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="owner"/>
	
	<acme:textbox code="comment.title" path="title"/>
	<br />

	<acme:textarea code="comment.text" path="text"/>
	<br />

	<acme:textbox code="comment.pictures" path="pictures"/>
	<br />

	<acme:textbox code="comment.rate" path="rate"/>
	<br />
	
	<acme:select items="${routes}" itemLabel="name" code="comment.route" path="route"/>
	<br/>

	<acme:select items="${hikes}" itemLabel="name" code="comment.hike" path="hike"/>
	<br/>
	
	<acme:textbox code="comment.writeMoment" path="writeMoment" placeholder="dd/MM/yyyy HH:mm" readonly="true"/>
	<br />
	
	<!-- Buttons -->
	<acme:cancel url="/welcome/index.do" code="comment.cancel"/>
	<acme:submit name="save" code="comment.save"/>
</form:form>
</security:authorize>