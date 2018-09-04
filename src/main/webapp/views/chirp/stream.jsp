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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<a href="" onclick="relativeRedir('/')"><spring:message code="common.action.back" /></a>
</div>
<security:authorize access="hasRole('USER') or hasRole('ADMIN')">
<display:table name="chirps" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<acme:column code="chirp.title" property="title"/>

<acme:column code="chirp.description" property="description"/>

<spring:message code="master.page.date.dateFormat" var="formatHeader"/>
<fmt:formatDate value="${c.postMoment}" pattern="${formatHeader}" var="formatVar"/>
<acme:column code="chirp.postMoment" property="postMoment" format="${formatVar}"/>

<spring:message code="chirp.user" var="userHeader"/>
<display:column title="${userHeader}" sortable="true">
<a href="user/display.do?userId=${c.user.id}"><jstl:out value="${c.user.name}" /> <jstl:out value="${c.user.surname}" /></a>
</display:column>

<security:authorize access="hasRole('ADMIN')">
<spring:message code="chirp.deleteTheChirp" var="deleteTheChirpHeader"/>
<display:column title="${deleteTheChirpHeader}" sortable="false">
<spring:message code="chirp.delete" var="deleteHeader"/>
	<a href="chirp/admin/delete.do?chirpId=${c.id}" onclick="return askSubmit('<spring:message code="common.message.confirm"/>')">${deleteHeader}</a>
</display:column>
</security:authorize>
</display:table>

</security:authorize>