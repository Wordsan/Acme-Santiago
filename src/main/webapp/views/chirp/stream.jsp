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

<security:authorize access="hasRole('USER')">
<display:table name="chirps" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<spring:message code="chirp.title" var="titleHeader"/>
<display:column property="title" title="${titleHeader}" sortable="false"/>

<spring:message code="chirp.postMoment" var="pmHeader"/>
<display:column property="postMoment" title="${pmHeader}" sortable="false"/>

<spring:message code="chirp.description" var="descriptionHeader"/>
<display:column property="description" title="${descriptionHeader}" sortable="false"/>

<spring:message code="chirp.user" var="userHeader"/>
<display:column property="user.name" title="${userHeader}" sortable="true"/>
</display:table>

<spring:message code="chirp.return" var="returnHeader"/>
<input type="button" name="return" value="${returnHeader}" onclick="javascript:relativeRedir('/welcome/index.do');"/>
</security:authorize>