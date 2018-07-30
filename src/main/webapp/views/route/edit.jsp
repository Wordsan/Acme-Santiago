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


<form:form action="route/user/edit.do" modelAttribute="route">

	<form:hidden path="id" />
	

	<acme:textbox code="route.name" path="name"/>
	<br />
	
	<acme:textbox code="route.length" path="length"/>
	<br />
	
	<acme:textarea code="route.description" path="description"/>
	<br />
		
	<acme:textbox code="route.pictures" path="pictures"/>
	<br />
	

	<!-- Buttons -->

	<acme:cancel url="route/user/myList.do" code="common.action.cancel"/>
		
	<acme:submit name="save" code="common.action.save" />
			
	<jstl:if test="${ route.id != 0 }">
		<acme:delete name="delete" code="common.action.delete" confirm="common.message.confirm" />
	</jstl:if>
</form:form>