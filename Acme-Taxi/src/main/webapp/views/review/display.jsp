<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="review.title" />:
	<jstl:out value="${review.title}" />
</p>
<p>
	<spring:message code="review.body" />:
	<jstl:out value="${review.body}" />
</p>
<p>
	<spring:message code="review.rating" />:
	<jstl:out value="${review.rating}" />
</p>
<%-- <spring:message code="review.dateFormat2" var="dateFormat" />
<p>
	<spring:message code="review.moment" />:
	<fmt:formatDate value="${review}" pattern="${dateFormat}" />
</p> --%>

<%-- <p>
	<spring:message code="review.creator" />:
	<jstl:out value="${review.creator}" />
</p> --%>

<jstl:if test="${requestURI == 'review/user/display.do'}">
<a href = "review/user/edit.do?reviewId=${review.id }">
<spring:message code="review.edit" />
</a>
</jstl:if>
