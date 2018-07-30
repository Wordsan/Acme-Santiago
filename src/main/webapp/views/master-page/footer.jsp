<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Santiago Co., Inc.</b>

<div class="footer-menu">
	<ul>
		<li>
			<a href="aboutus.do"><spring:message code="master.page.footermenu.aboutus" /></a>
		</li>
		<li>
			<a href="terms.do"><spring:message code="master.page.footermenu.terms" /></a>
		</li>
		<li>
			<a href="privacy.do"><spring:message code="master.page.footermenu.privacy" /></a>
		</li>
		<li>
			<a href="cookies.do"><spring:message code="master.page.footermenu.cookies" /></a>
		</li>
	</ul>
</div>
<div id="cookie-notice" class="cookie-notice hidden">
	<div id="close-button" class="close-button">X</div>
	<div class="cookie-notice-title">
		<spring:message code="master.page.cookies.notice.title" />
	</div>
	<div class="cookie-notice-text">
		<spring:message code="master.page.cookies.notice.text" />
		<a href="cookies.do"><spring:message code="master.page.cookies.textlink" /></a>
	</div>
	<div class="text-center button-container">
		<a id="cookie-notice-button" class="button button-success text-bold"><spring:message code="master.page.cookies.accept" /></a>
	</div>
</div>