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


<form:form action="hike/user/edit.do" modelAttribute="hike">

	<form:hidden path="id" />
	<jstl:if test="${ hike.id == 0 }">
		<form:hidden path="route" />
	</jstl:if>
	
	<acme:textbox code="hike.name" path="name"/>
	<br/>
	
	<acme:textarea code="hike.description" path="description"/>
	<br/>
	
	<acme:select itemLabel="--" onlyValues="true" items="${difficultyLevelOptions}" code="hike.difficultyLevel" path="difficultyLevel"/>
	<br/>
	
	<acme:textbox code="hike.length" path="length"/>
	<br/>
	
	<acme:textbox code="hike.pictures" path="pictures"/>
	<br/>
	
	<acme:textbox code="hike.originCity" path="originCity"/>
	<br/>
	
	<acme:textbox code="hike.destinationCity" path="destinationCity"/>
	<br/>

	<!-- Buttons -->

	<acme:cancel url="/route/display.do?routeId=${ hike.route.id }" code="common.action.cancel"/>
		
	<acme:submit name="save" code="common.action.save" />
	
	<jstl:if test="${hike.id != 0}">
		<acme:delete name="delete" code="common.action.delete" confirm="common.message.confirm" />
	</jstl:if>
			
</form:form>