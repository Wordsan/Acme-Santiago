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

<div>
	<div>
		<h3><spring:message code="legal.aboutus.companyname" /></h3>
		<p><jstl:out value="${companyName}"/></p>
	</div>
	<div>
		<h3><spring:message code="legal.aboutus.address" /></h3>
		<p><jstl:out value="${address}"/></p>
	</div>
	<div>
		<h3><spring:message code="legal.aboutus.vatnumber" /></h3>
		<p><jstl:out value="${vatNumber}"/></p>
	</div>
	<div>
		<h3><spring:message code="legal.aboutus.contactemail" /></h3>
		<p><jstl:out value="${contactEmail}"/></p>
	</div>
</div>
