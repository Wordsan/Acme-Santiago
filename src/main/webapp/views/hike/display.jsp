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

<div>
	<a href="route/display.do?routeId=${hike.route.id}"><spring:message code="common.action.back"/></a>
</div>

<p>
	<div>
		<strong><spring:message code="hike.name" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.name }" />
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
		<strong><spring:message code="hike.difficultyLevel" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.difficultyLevel }" />
	</div>
</p>


<p>
	<div>
		<strong><spring:message code="hike.length" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.length }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.pictures" />:</strong>
	</div>
	<div>
		<img src="<jstl:out value="${ hike.pictures }" />" height="200px;" width="auto"/>
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.originCity" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.originCity }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.destinationCity" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.destinationCity }" />
	</div>
</p>