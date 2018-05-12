<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="mechanic.username" />
	:
	<jstl:out value="${mechanic.userAccount.username}" />
</p>
<p>
	<spring:message code="mechanic.name" />
	:
	<jstl:out value="${mechanic.name}" />
</p>
<p>
	<spring:message code="mechanic.surname" />
	:
	<jstl:out value="${mechanic.surname}" />
</p>
<spring:message code="mechanic.dateFormat2" var="dateFormat2" />
<p>
<spring:message code="mechanic.birthdate"/>:<fmt:formatDate value="${mechanic.birthdate}" pattern="${dateFormat2}" />
</p>

<p>
	<spring:message code="mechanic.idNumber" />
	:
	<jstl:out value="${mechanic.idNumber}" />
</p>
<jstl:if test='${not empty mechanic.phone}'>
<p>
	<spring:message code="mechanic.phone" />
	:
	<jstl:out value="${mechanic.phone}" />
</p>
</jstl:if>
<jstl:if test='${not empty mechanic.photoUrl}'>
<spring:message code="mechanic.photo" var="mechanicPhoto" />
<p>
	<img src="${mechanic.photoUrl}" alt="${mechanicPhoto}" />
</p>
</jstl:if>
<p>
	<spring:message code="mechanic.email" />
	:
	<jstl:out value="${mechanic.email}" />
</p>

