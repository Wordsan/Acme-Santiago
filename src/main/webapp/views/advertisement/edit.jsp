<%--
 * edit.jsp
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


<form:form action="${requestUri}" modelAttribute="advertisement">

	<form:hidden path="id" />
	
	<b><spring:message code="advert.info" /></b>
	<br/>
	<br/>
	
	<acme:textbox code="advert.title" path="title"/>
	<br/>
	
	<acme:textbox code="advert.banner" path="banner"/>
	<br/>
	
	<acme:textbox code="advert.targetUrl" path="targetUrl"/>
	<br/>
	
	<acme:textbox code="advert.startMoment" path="startMoment" readonly="true"/>
	<br/>
	
	<acme:textbox code="advert.daysDisplayed" path="daysDisplayed"/>
	<br/>
	
	<acme:select itemLabel="name" code="advert.hike" items="${hikes}" path="hike"/>
	<br/>
	
	<b><spring:message code="advert.creditCard" /></b>
	<br/>
	<br/>
	
	<acme:textbox code="advert.creditCard.holderName" path="creditCard.holderName"/>
	<br />
	
	<acme:textbox code="advert.creditCard.brandName" path="creditCard.brandName"/>
	<br />
	
	<acme:textbox code="advert.creditCard.cardNumber" path="creditCard.cardNumber"/>
	<br />
	
	<acme:textbox code="advert.creditCard.expirationMonth" path="creditCard.expirationMonth"/>
	<br />
	
	<acme:textbox code="advert.creditCard.expirationYear" path="creditCard.expirationYear"/>
	<br />
	
	<acme:textbox code="advert.creditCard.cvvCode" path="creditCard.cvvCode"/>
	<br />

	<!-- Buttons -->

	<acme:cancel url="/advertisement/agent/myList.do" code="common.action.cancel"/>
		
	<acme:submit name="save" code="common.action.save" />
	
	<jstl:if test="${advertisement.id != 0}">
		<acme:delete name="delete" code="common.action.delete" confirm="common.message.confirm" />
	</jstl:if>
			
</form:form>