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
		<strong><spring:message code="user.name" />:</strong>
	</div>
	<div>
		<jstl:out value="${ user.name }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.surname" />:</strong>
	</div>
	<div>
		<jstl:out value="${ user.surname }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.picture" />:</strong>
	</div>
	<div>
		<img src="<jstl:out value="${ user.picture }" />" height="200px;" width="auto"/>
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.postalAddress" />:</strong>
	</div>
	<div>
		<jstl:out value="${ user.postalAddress }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.phone" />:</strong>
	</div>
	<div>
		<jstl:out value="${ user.phoneNumber }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.email" />:</strong>
	</div>
	<div>
		<jstl:out value="${ user.emailAddress }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.routes" />:</strong>
	</div>
	<div>
		<display:table name="routes" id="row" requestURI="${requestURI}" pagesize="3" class="displaytag">
	
			<spring:message code="route.name" var="nameHeader"  />
			<display:column property="name" title="${nameHeader}" />
			
			<spring:message code="route.length" var="lengthHeader"  />
			<display:column property="length" title="${valueHeader}" />
			
			<spring:message code="route.numHikes" var="numHikesHeader"  />
			<display:column title="${numHikesHeader}">
				<jstl:out value="${ row.composedHikes.size() }" />
			</display:column>
			
			<spring:message var="viewHeader" code="common.action.view" />
			<display:column title="${viewHeader}">
				<a href="route/display.do?routeId=${row.id}"> <spring:message code="common.action.view" /></a>
			</display:column>
			
		</display:table>
	</div>
</p>