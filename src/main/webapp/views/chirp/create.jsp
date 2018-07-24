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
	
	
	<form:label path="postMoment" >
	<spring:message code="chirp.postMoment"/>:
	</form:label>
	<form:input path="postMoment" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="postMoment"/>
	<br />
	
	
	<form:label path="title">
	<spring:message code="chirp.title"/>:
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />

	
	<form:label path="description">
	<spring:message code="chirp.description"/>:
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<!-- Buttons -->
	<spring:message code="chirp.save" var="saveHeader"/>
	<input type="submit" name="save" value="${saveHeader}"/>
	<spring:message code="chirp.cancel" var="cancelHeader"/>
	<input type="button" name="cancel" value="${cancelHeader}" onclick="javascript:relativeRedir('/welcome/index.do');"/>
</form:form>
</security:authorize>