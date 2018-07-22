<%--
 * list.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<div>
		<strong><spring:message code="route.name" />:</strong>
	</div>
	<div>
		<jstl:out value="${ route.name }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.length" />:</strong>
	</div>
	<div>
		<jstl:out value="${ route.length }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.description" />:</strong>
	</div>
	<div>
		<jstl:out value="${ route.description }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.pictures" />:</strong>
	</div>
	<div>
		<img src="<jstl:out value="${ route.name }" />" height="200px;" width="auto"/>
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.hikes" />:</strong>
	</div>
	<div>
	</div>
</p>