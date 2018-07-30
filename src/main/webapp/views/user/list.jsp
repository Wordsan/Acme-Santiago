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


<display:table name="users" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<spring:message code="user.name" var="nameHeader"  />
	<display:column property="name" title="${nameHeader}" />
	
	<spring:message code="user.surname" var="surnameHeader"  />
	<display:column property="surname" title="${surnameHeader}" />
	
	<spring:message var="viewHeader" code="common.action.view" />
	<display:column title="${viewHeader}">
		<a href="user/display.do?userId=${row.id}"> <spring:message code="common.action.view" /></a>
	
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${(followedUsers != null) && (row != null)}">
			<spring:eval var="isFollowed" expression="followedUsers.contains(row)" />
			<display:column>
				<jstl:if test="${isFollowed && (row.id != user.id)}">
					<a href="user/user/unfollow.do?userId=${row.id}"> <spring:message code="user.unfollow" /></a>
				</jstl:if>
				<jstl:if test="${!isFollowed && (row.id != user.id)}">
					<a href="user/user/follow.do?userId=${row.id}"> <spring:message code="user.follow" /></a>
				</jstl:if>
			</display:column>
		</jstl:if>
	</security:authorize>
</display:table>