<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Santiago Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="configurationSystem/admin/edit.do"><spring:message code="master.page.editTabooWords" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.taboo" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="chirp/admin/list.do"><spring:message code="master.page.tabooChirps" /></a></li>
					<li><a href="comment/admin/list.do"><spring:message code="master.page.tabooComments" /></a></li>
				</ul>
			</li>
			<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.chirps" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="chirp/user/create.do"><spring:message code="master.page.createChirp" /></a></li>
					<li><a href="chirp/user/streamChirps.do"><spring:message code="master.page.streamChirp" /></a></li>
				</ul>
			</li>
			<li><a href="comment/user/create.do"><spring:message code="master.page.createComment" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('INNKEEPER')">
		</security:authorize>
		
		<security:authorize access="permitAll">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.routes" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="route/list.do"><spring:message code="master.page.routes.browse" /></a></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="route/user/myList.do"><spring:message code="master.page.routes.myList" /></a></li>
					</security:authorize>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.users" /> 
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="user/list.do"><spring:message code="master.page.users.browse" /></a></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="user/user/followedUsers.do"><spring:message code="master.page.users.follow" /></a></li>
						<li><a href="user/user/followerUsers.do"><spring:message code="master.page.users.follower" /></a></li>
					</security:authorize>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/user/signin.do"><spring:message code="master.page.signin" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

