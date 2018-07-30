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
		<h2><spring:message code="legal.terms.block1.title" /></h2>
		<p>
			<spring:message code="legal.terms.block1.text1" />
			<span class="text-bold"> <jstl:out value="${companyName}" />, </span>
			<spring:message code="legal.terms.block1.text2" />
			<span class="text-bold"> <jstl:out value="${address}" /> </span>
			<spring:message code="legal.terms.block1.text3" />
			<span class="text-bold"> <jstl:out value="${vatNumber}" />, </span>
			<spring:message code="legal.terms.block1.text4" />
		</p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block2.title" /></h2>
		<p><spring:message code="legal.terms.block2.text1" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block3.title" /></h2>
		<p><spring:message code="legal.terms.block3.text1" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block4.title" /></h2>
		<p><spring:message code="legal.terms.block4.text1" /></p>
		<p><spring:message code="legal.terms.block4.text2" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block5.title" /></h2>
		<p><spring:message code="legal.terms.block5.text1" /></p>
		<p><spring:message code="legal.terms.block5.text2" /></p>
		<p><spring:message code="legal.terms.block5.text3" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block6.title" /></h2>
		<p><spring:message code="legal.terms.block6.text1" /></p>
		<p><spring:message code="legal.terms.block6.text2" /></p>
	</div>
	<div>
		<h2><spring:message code="legal.terms.block7.title" /></h2>
		<p><spring:message code="legal.terms.block7.text1" /></p>
		<p>
			<spring:message code="legal.terms.block7.text2" />
			<span class="text-bold"> <jstl:out value="${contactEmail}" />. </span>
		</p>
	</div>
	
	<div>
		<h2><spring:message code="legal.terms.block8.title" /></h2>
		<p>
			<spring:message code="legal.terms.block8.text1" /> 
			<span class="text-bold"><jstl:out value="${tabooWords}"/></span><spring:message code="legal.terms.block8.text2" /> 
		</p>
		<p>
			<spring:message code="legal.terms.block8.text3" />
		</p>
	</div>
</div>