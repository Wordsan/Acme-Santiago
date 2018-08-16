<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%-- Attributes --%>
<%@ attribute name="property" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="sortable" required="false" %>
<%@ attribute name="format" required="false" %>

<jstl:if test="${sortable == null}">
	<jstl:set var="sortable" value="false" />
</jstl:if>

<jstl:if test="${format == null}">
	<jstl:set var="format" value="" />
</jstl:if>

<%-- Definition --%>
<spring:message code="${code}" var="codeHeader"/>
<display:column property="${property}" title="${codeHeader}" sortable="${sortable}" format="${format}"/>
