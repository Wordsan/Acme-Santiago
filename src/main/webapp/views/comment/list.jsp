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

<security:authorize access="hasRole('ADMIN')">
<display:table name="comments" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<spring:message code="comment.title" var="titleHeader"/>
<display:column property="title" title="${titleHeader}" sortable="false"/>

<spring:message code="comment.writeMoment" var="pmHeader"/>
<display:column property="writeMoment" title="${pmHeader}" sortable="false"/>

<spring:message code="comment.text" var="textHeader"/>
<display:column property="text" title="${textHeader}" sortable="false"/>

<spring:message code="comment.pictures" var="picturesHeader"/>
<display:column property="pictures" title="${picturesHeader}" sortable="false"/>

<spring:message code="comment.rate" var="rateHeader"/>
<display:column property="rate" title="${rateHeader}" sortable="false"/>
	
<spring:message code="comment.owner" var="ownerHeader"/>
<display:column property="owner.name" title="${ownerHeader}" sortable="true"/>

<spring:message code="comment.route" var="routeHeader"/>
<display:column property="route.name" title="${routeHeader}" sortable="true"/>

<spring:message code="comment.hike" var="hikeHeader"/>
<display:column property="hike.name" title="${hikeHeader}" sortable="true"/>

<spring:message code="comment.deleteTheComment" var="deleteTheCommentHeader"/>
<display:column title="${deleteTheCommentHeader}" sortable="false">
<spring:message code="comment.delete" var="deleteHeader"/>
<a href="comment/admin/delete.do?commentId=${c.id}">${deleteHeader}</a>
</display:column>
</display:table>

<spring:message code="comment.return" var="returnHeader"/>
<input type="button" name="return" value="${returnHeader}" onclick="javascript:relativeRedir('/welcome/index.do');"/>
</security:authorize>