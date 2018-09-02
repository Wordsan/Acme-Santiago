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
	<a href="" onclick="relativeRedir(window.history.go(-1))"><spring:message code="common.action.back" /></a>
</div>

<p>
	<div>
		<strong><spring:message code="user.name" />:</strong>
		<jstl:out value="${ user.name }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.surname" />:</strong>
		<jstl:out value="${ user.surname }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.picture" />:</strong>
		<img src="<jstl:out value="${ user.picture }" />" height="100px;" width="auto"/>
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.postalAddress" />:</strong>
		<jstl:out value="${ user.postalAddress }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.phone" />:</strong>
		<jstl:out value="${ user.phoneNumber }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="user.email" />:</strong>
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
			<display:column property="length" title="${lengthHeader}" />
			
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