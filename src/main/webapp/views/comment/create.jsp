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
	
	<form:label path="title">
	<spring:message code="comment.title"/>:
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title"/>
	<br />

	<form:label path="text">
	<spring:message code="comment.text"/>:
	</form:label>
	<form:input path="text"/>
	<form:errors cssClass="error" path="text"/>
	<br />

	<form:label path="pictures">
	<spring:message code="comment.pictures"/>:
	</form:label>
	<form:input path="pictures"/>
	<form:errors cssClass="error" path="pictures"/>
	<br />

	<form:label path="rate">
	<spring:message code="comment.rate"/>:
	</form:label>
	<form:input path="rate"/>
	<form:errors cssClass="error" path="rate"/>
	<br />
	
	<form:label path="route">
	<spring:message code="comment.route"/>:
	</form:label>
	<form:select path="route">
		<form:option label="----" value="0"/>
		<form:options items="${routes}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="route"/>
	<br/>

	<form:label path="hike">
	<spring:message code="comment.hike"/>:
	</form:label>
	<form:select path="hike">
		<form:option label="----" value="0"/>
		<form:options items="${hikes}" itemLabel="name" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="hike"/>
	<br/>
	
	<!-- Buttons -->
	<spring:message code="comment.save" var="saveHeader"/>
	<input type="submit" name="save" value="${saveHeader}"/>
	<spring:message code="comment.cancel" var="cancelHeader"/>
	<input type="button" name="cancel" value="${cancelHeader}" onclick="javascript:relativeRedir('/welcome/index.do');"/>
</form:form>
</security:authorize>