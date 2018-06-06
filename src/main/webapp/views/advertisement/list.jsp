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
	
	<jsp:useBean id="date" class="java.util.Date" />
	<display:table name="advertisements" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

		
		<spring:message code="advert.title" var="titleHeader"  />
		<display:column property="title" title="${titleHeader}" />
		
		<spring:message code="advert.banner" var="bannerHeader"  />
		<display:column title="${bannerHeader}">
			<a target="_blank" href="<jstl:out value="${row.banner}"/>">
				<jstl:out value="${row.banner}"/>
			</a>
		</display:column>
		
		<spring:message code="advert.targetUrl" var="targetUrlHeader"  />
		<display:column title="${targetUrlHeader}">
			<a target="_blank" href="<jstl:out value="${row.targetUrl}"/>">
				<jstl:out value="${row.targetUrl}"/>
			</a>
		</display:column>
		
		<spring:message code="advert.startMoment" var="startMomentHeader"  />
		<display:column property="startMoment" title="${startMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
		
		<spring:message code="advert.daysDisplayed" var="daysDisplayedHeader"  />
		<display:column property="daysDisplayed" title="${daysDisplayedHeader}"/>
		
		<security:authorize access="hasRole('AGENT')">
				<spring:message var="editHeader" code="common.action.edit" />
				<display:column title="${editHeader}">
				<jstl:if test="${date le row.endMoment}">
					<a href="advertisement/agent/edit.do?advertisementId=${row.id}"> 
						<spring:message code="common.action.edit" />
					</a>
					
				</jstl:if>
				</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
				<spring:message var="deleteHeader" code="common.action.delete" />
				<display:column title="${deleteHeader}">
					<jstl:if test="${date le row.endMoment}">
					<a href="advertisement/administrator/delete.do?advertisementId=${row.id}" onclick="return askSubmit('<spring:message code="common.message.confirm"/>')"> 
						<spring:message code="common.action.delete" />
					</a>
					</jstl:if>
				</display:column>
		</security:authorize>
		
	</display:table>
</div>
<security:authorize access="hasRole('AGENT')">
<div>
	<a href="advertisement/agent/create.do"> 
		<spring:message code="advert.create" />
	</a>
</div>
</security:authorize>