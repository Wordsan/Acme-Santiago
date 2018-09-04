<%--
 * list.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<a href="" onclick="relativeRedir(window.history.go(-1))"><spring:message code="common.action.back" /></a>
</div>

<p>
	<div>
		<strong><spring:message code="hike.name" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.name }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.description" />:</strong>
	</div>
	<div>
		<jstl:out value="${ route.description }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.difficultyLevel" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.difficultyLevel }" />
	</div>
</p>


<p>
	<div>
		<strong><spring:message code="hike.length" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.length }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.pictures" />:</strong>
	</div>
	<div>
		<jstl:forEach var="picture" items="${pictures}">
		<img src="<jstl:out value="${picture}" />" height="200px;" width="auto"/>
		</jstl:forEach>
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.originCity" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.originCity }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="hike.destinationCity" />:</strong>
	</div>
	<div>
		<jstl:out value="${ hike.destinationCity }" />
	</div>
</p>

<p>
	<div>
		<strong><spring:message code="route.comments" />:</strong>
	</div>
	<div>
		<display:table name="comments" id="row" requestURI="${requestURI}" pagesize="3" class="displaytag">
	
			<spring:message code="comment.title" var="titleHeader"  />
			<display:column property="title" title="${titleHeader}" />
			
			<spring:message code="comment.text" var="textHeader"  />
			<display:column property="text" title="${textHeader}" />
			
			<spring:message code="comment.rate" var="rateHeader"  />
			<display:column property="rate" title="${rateHeader}" />
			
			<spring:message code="comment.writeMoment" var="writeMomentHeader"  />
			<display:column property="writeMoment" title="${writeMomentHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
			
			<spring:message code="comment.owner" var="ownerHeader"  />
			<display:column title="${ownerHeader}">
				<a href="user/display.do?userId=${row.owner.id}"><jstl:out value="${row.owner.name}"/> <jstl:out value="${row.owner.surname}"/></a>
			</display:column>
		</display:table>
		
		<security:authorize access="hasRole('USER')">
			<jstl:if test="${(user != null)}">
				<div>
					<a href="comment/user/create.do?routeOrhikeId=${hike.id}"> <spring:message code="comment.create" /></a>
				</div>
			</jstl:if>
		</security:authorize>
	</div>
</p>