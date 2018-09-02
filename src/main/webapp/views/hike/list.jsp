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
	<display:table name="hikes" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

		
		<spring:message code="hike.name" var="nameHeader"  />
		<display:column property="name" title="${nameHeader}" />
		
		<spring:message code="hike.description" var="descriptionHeader"  />
		<display:column property="description" title="${descriptionHeader}" />
				
		<spring:message code="hike.route" var="nameHeader"  />
		<display:column property="route.name" title="${nameHeader}" />
		
		<spring:message var="viewHeader" code="common.action.view" />
		<display:column title="${viewHeader}">
			<a href="hike/display.do?hikeId=${row.id}"> <spring:message code="common.action.view" /></a>
		</display:column>
				
	</display:table>
</div>