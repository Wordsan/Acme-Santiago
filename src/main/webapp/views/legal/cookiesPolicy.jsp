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
		<p><spring:message code="legal.cookies.aboutpolicy" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.cookies.aboutcookies.title" /></h2>
		<p><spring:message code="legal.cookies.aboutcookies.text" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.cookies.typescookies.title" /></h2>
		<p><spring:message code="legal.cookies.typescookies.text1" /></p>
		<p><spring:message code="legal.cookies.typescookies.text2" /></p>
		<p><spring:message code="legal.cookies.typescookies.text3" /></p>
		<p>
			<spring:message code="legal.cookies.typescookies.text4" />
			<a href="http://www.agpd.es/portalwebAGPD/canaldocumentacion/publicaciones/common/Guias/Guia_Cookies.pdf"><spring:message code="legal.cookies.typescookies.arpdlink" /></a>
		</p>
	</div>
	<div>
		<h2><spring:message code="legal.cookies.usedcookies.title" /></h2>
		<p><spring:message code="legal.cookies.usedcookies.text1" /></p>
		<p><spring:message code="legal.cookies.usedcookies.cookie1" /></p>
		<p><spring:message code="legal.cookies.usedcookies.cookie2" /></p>
		<p><spring:message code="legal.cookies.usedcookies.cookie3" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.cookies.modify.title" /></h2>
		<p><spring:message code="legal.cookies.modify.text" /></p>
	</div>
</div>
