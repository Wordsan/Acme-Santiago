<%--
 * stream.jsp
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

<div>
	<a href="" onclick="relativeRedir('/')"><spring:message code="common.action.back" /></a>
</div>
<security:authorize access="hasRole('ADMIN')">
<display:table name="comments" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<acme:column code="comment.title" property="title"/>

<acme:column code="comment.text" property="text"/>

<acme:column code="comment.writeMoment" property="writeMoment" format="{0,date,dd/MM/yyyy HH:mm}"/>

<acme:column code="comment.pictures" property="pictures"/>

<acme:column code="comment.rate" property="rate"/>


<spring:message code="comment.owner" var="ownerHeader"/>
<display:column title="${ownerHeader}" sortable="true">
<a href="user/display.do?userId=${c.owner.id}"><jstl:out value="${c.owner.name}" /> <jstl:out value="${c.owner.surname}" /></a>
</display:column>

<spring:message code="comment.route" var="routeHeader"/>
<display:column title="${routeHeader}" sortable="true">
<a href="route/display.do?routeId=${c.route.id}"><jstl:out value="${c.route.name}" /></a>
</display:column>

<spring:message code="comment.hike" var="hikeHeader"/>
<display:column title="${hikeHeader}" sortable="true">
<a href="hike/display.do?hikeId=${c.hike.id}"><jstl:out value="${c.hike.name}" /></a>
</display:column>

<spring:message code="comment.deleteTheComment" var="deleteTheCommentHeader"/>
<display:column title="${deleteTheCommentHeader}" sortable="false">
<spring:message code="comment.delete" var="deleteHeader"/>
<a href="comment/admin/delete.do?commentId=${c.id}" onclick="return askSubmit('<spring:message code="common.message.confirm"/>')">${deleteHeader}</a>
</display:column>
</display:table>
</security:authorize>