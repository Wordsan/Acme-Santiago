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

<security:authorize access="hasRole('USER') or hasRole('ADMIN')">
<display:table name="chirps" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<acme:column code="chirp.title" property="title"/>

<acme:column code="chirp.postMoment" property="postMoment"/>

<acme:column code="chirp.description" property="description"/>

<acme:column code="chirp.user" property="user.name" sortable="true"/>

<security:authorize access="hasRole('ADMIN')">
<spring:message code="chirp.deleteTheChirp" var="deleteTheChirpHeader"/>
<display:column title="${deleteTheChirpHeader}" sortable="false">
<spring:message code="chirp.delete" var="deleteHeader"/>
<a href="chirp/admin/delete.do?chirpId=${c.id}">${deleteHeader}</a>
</display:column>
</security:authorize>
</display:table>

<acme:cancel url="/welcome/index.do" code="chirp.return"/>
</security:authorize>