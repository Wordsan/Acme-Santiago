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

<security:authorize access="hasRole('ADMIN')">
<display:table name="comments" id="c" requestURI="${requestURI}" pagesize="4" class="displaytag">

<acme:column code="comment.title" property="title"/>

<acme:column code="comment.writeMoment" property="writeMoment"/>

<acme:column code="comment.text" property="text"/>

<acme:column code="comment.pictures" property="pictures"/>

<acme:column code="comment.rate" property="rate"/>

<acme:column code="comment.owner" property="owner.name" sortable="true"/>	

<acme:column code="comment.route" property="route.name" sortable="true"/>

<acme:column code="comment.hike" property="hike.name" sortable="true"/>


<spring:message code="comment.deleteTheComment" var="deleteTheCommentHeader"/>
<display:column title="${deleteTheCommentHeader}" sortable="false">
<spring:message code="comment.delete" var="deleteHeader"/>
<a href="comment/admin/delete.do?commentId=${c.id}">${deleteHeader}</a>
</display:column>
</display:table>

<acme:cancel url="/welcome/index.do" code="comment.return"/>
</security:authorize>