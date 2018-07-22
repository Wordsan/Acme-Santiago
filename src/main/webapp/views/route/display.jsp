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
		<display:table name="hikes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
			<spring:message code="route.name" var="nameHeader"  />
			<display:column property="name" title="${nameHeader}" />
			
			<spring:message code="route.description" var="descriptionHeader"  />
			<display:column property="description" title="${descriptionHeader}" />
			
			<spring:message var="viewHeader" code="common.action.view" />
			<display:column title="${viewHeader}">
				<a href="hike/display.do?hikeId=${row.id}"> <spring:message code="common.action.view" /></a>
			</display:column>
			
			<security:authorize access="hasRole('USER')">
				<jstl:if test="${(user != null) && (user.id == route.creator.id)} ">
					<spring:message var="editHeader" code="common.action.edit" />
					<display:column title="${editHeader}">
						<a href="hike/user/edit.do?hikeId=${row.id}"> <spring:message code="common.action.edit" /></a>
					</display:column>
				</jstl:if>
			</security:authorize>
			
		</display:table>
		<security:authorize access="hasRole('USER')">
			<jstl:if test="${(user != null) && (user.id == route.creator.id)} ">
				<spring:message var="createHeader" code="common.action.create" />
				<display:column title="${createHeader}">
					<a href="hike/user/create.do?routeId=${route.id}"> <spring:message code="common.action.create" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>
	</div>
</p>