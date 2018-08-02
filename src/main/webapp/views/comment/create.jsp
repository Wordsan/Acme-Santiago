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
	<form:hidden path="writeMoment"/>
	
	<acme:textbox code="comment.title" path="title"/>
	<br />

	<acme:textbox code="comment.text" path="text"/>
	<br />

	<acme:textbox code="comment.pictures" path="pictures"/>
	<br />

	<acme:textbox code="comment.rate" path="rate"/>
	<br />
	
	<acme:select items="${routes}" itemLabel="name" code="comment.route" path="route"/>
	<br/>

	<acme:select items="${hikes}" itemLabel="name" code="comment.hike" path="hike"/>
	<br/>
	
	<!-- Buttons -->
	<acme:submit name="save" code="comment.save"/>
	<acme:cancel url="/welcome/index.do" code="comment.cancel"/>
</form:form>
</security:authorize>