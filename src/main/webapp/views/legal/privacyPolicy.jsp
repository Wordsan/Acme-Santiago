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
		<p>
			<span class="text-bold"><jstl:out value="${companyName}" />,</span>
			<spring:message code="legal.privacy.text1" />
		</p>
		<p>
			<span class="text-bold"><jstl:out value="${companyName}" />,</span>
			<spring:message code="legal.privacy.text2" />
		</p>
		<p>
			<span class="text-bold"><jstl:out value="${companyName}" />,</span>
			<spring:message code="legal.privacy.text3" />
		</p>
		<p>
			<spring:message code="legal.privacy.text4" />
		</p>
		<div>
			<ol>
				<li><spring:message code="legal.privacy.list1" /></li>
				<li><spring:message code="legal.privacy.list2" /></li>
				<li><spring:message code="legal.privacy.list3" /></li>
				<li><spring:message code="legal.privacy.list4" /></li>
				<li><spring:message code="legal.privacy.list5" /></li>
			</ol>
		</div>
		<p>
			<spring:message code="legal.privacy.text51" />
			<span class="text-bold"><jstl:out value="${companyName}" />,</span>
			<spring:message code="legal.privacy.text52" />
			<jstl:out value="${address}" />
			<spring:message code="legal.privacy.text53" />
			<jstl:out value="${contactEmail}" />
			<spring:message code="legal.privacy.text54" />
		</p>
	</div>
</div>
