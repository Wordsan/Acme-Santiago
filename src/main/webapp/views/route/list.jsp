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

<jstl:if test="${ user == null }">
<div style="position: relative; margin-bottom: 20px;">
	<div style="display:inline-block; padding-right: 2%;">
		<form action="${requestURI}" method="get">
			<div style="padding-bottom: 10px;">
				<label for="keyword"><strong><spring:message code="route.search" />:</strong></label>
			</div>
			<div style="padding-bottom: 10px;">
				<input type="text" name="keyword" value="<jstl:out value="${ keyword }" />" id="keyword" placeholder="<spring:message code="route.search" />" />
			</div>
			<div style="padding-bottom: 10px;">
				<button type="submit"><spring:message code="route.search" /></button>
			</div>
		</form>	
	</div>
	
	<div style="display:inline-block; padding-right: 2%;">
		<form action="${requestURI}" method="get">
			<div style="padding-bottom: 10px;">
				<strong><spring:message code="route.lengthFilter" />:</strong>
			</div>
			<div style="padding-bottom: 10px;">
				<label for="min-length"><spring:message code="common.statistics.min" /></label>
				<input type="number" name="minLength" value="<jstl:out value="${ minLength }" />" id="min-length" min="0" max="999999"/>
				<label for="max-length"><spring:message code="common.statistics.max" /></label>
				<input type="number" name="maxLength" value="<jstl:out value="${ maxLength }" />" id="max-length" min="0" max="999999"/>
			</div>
			<div style="padding-bottom: 10px;">
				<button type="submit"><spring:message code="route.filter" /></button>
			</div>
		</form>
	</div>
	
	<div style="display:inline-block; padding-right: 2%; position: absolute; top: auto;">
		<form action="${requestURI}" method="get">
			<div style="padding-bottom: 10px;">
				<strong><spring:message code="route.numHikesFilter" />:</strong>
			</div>
			<input type="hidden" name="numHikesFilter" value="1" />
			<div style="padding-bottom: 10px;">
				<button type="submit" style=""><spring:message code="route.numHikesFilter.action" /></button>
			</div>
		</form>
	</div>
</div>
</jstl:if>

<div style="position: relative;">
<display:table name="routes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

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
	
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${(user != null) && (user.id == row.creator.id)}">
			<spring:message var="editHeader" code="common.action.edit" />
			<display:column title="${editHeader}">
				<a href="route/user/edit.do?routeId=${row.id}"> <spring:message code="common.action.edit" /></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message var="deleteHeader" code="common.action.delete" />
		<display:column title="${deleteHeader}">
			<a href="route/administrator/delete.do?routeId=${row.id}" onclick="return askSubmit('<spring:message code="common.message.confirm"/>')">
				<spring:message code="common.action.delete" />
			</a>
		</display:column>
	</security:authorize>
</display:table>
</div>
<jstl:if test="${ user != null }">
<div>
	<a href="route/user/create.do"><spring:message code="route.create"/></a>
</div>
</jstl:if>