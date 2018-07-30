<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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

<h2>
	<spring:message code="administrator.dashboard.routesPerUser"/>
</h2>
<display:table name="statistics.routesPerUser" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row.AVG }" />
	
	<spring:message code="common.statistics.stddev" var="stddevHeader"/>
	<display:column title="${stddevHeader}" value="${ row.STD }" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.hikesPerRoute"/>
</h2>
<display:table name="statistics.hikesPerRoute" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row.AVG }" />
	
	<spring:message code="common.statistics.stddev" var="stddevHeader"/>
	<display:column title="${stddevHeader}" value="${ row.STD }" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.routesLength"/>
</h2>
<display:table name="statistics.routesLength" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row.AVG }" />
	
	<spring:message code="common.statistics.stddev" var="stddevHeader"/>
	<display:column title="${stddevHeader}" value="${ row.STD }" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.hikesLength"/>
</h2>
<display:table name="statistics.hikesLength" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row.AVG }" />
	
	<spring:message code="common.statistics.stddev" var="stddevHeader"/>
	<display:column title="${stddevHeader}" value="${ row.STD }" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.outlierRoutes"/>
</h2>
<display:table name="statistics.outlierRoutes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="route.name" var="nameHeader"/>
	<display:column title="${nameHeader}" property="name"/>
	
	<spring:message code="route.length" var="lengthHeader"/>
	<display:column title="${lengthHeader}" property="length" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.chirpsPerUser"/>
</h2>
<display:table name="statistics.chirpsPerUser" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row }" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.users75PercChirps"/>
</h2>
<display:table name="statistics.users75PercentChirps" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="user.name" var="nameHeader"/>
	<display:column title="${nameHeader}" property="name" />
	
	<spring:message code="user.surname" var="surnameHeader"/>
	<display:column title="${surnameHeader}" property="surname" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.users25PercChirps"/>
</h2>
<display:table name="statistics.users25PercentChirps" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="user.name" var="nameHeader"/>
	<display:column title="${nameHeader}" property="name" />
	
	<spring:message code="user.surname" var="surnameHeader"/>
	<display:column title="${surnameHeader}" property="surname" />
</display:table>

<h2>
	<spring:message code="administrator.dashboard.commentsPerRoute"/>
</h2>
<display:table name="statistics.commentsPerRoute" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="common.statistics.avg" var="avgHeader"/>
	<display:column title="${avgHeader}" value="${ row }" />
</display:table>