<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri ="http://java.sun.com/jsp/jstl/functions"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="service.title" />:
	<jstl:out value="${service.title}" />
</p>
<p>
	<spring:message code="service.price" />:
	<jstl:out value="${service.price}" /><jstl:out value="${currency}"/>
</p>
<jstl:if test='${pendingReservations==0}'>
<p>
	<a href="service/mechanic/edit.do?serviceId=${service.id}"> <spring:message code="service.edit" />
	</a>
	
</p>
</jstl:if>

