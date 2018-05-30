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
	<spring:message code="admin.username" />
	:
	<jstl:out value="${admin.userAccount.username}" />
</p>
<p>
	<spring:message code="admin.name" />
	:
	<jstl:out value="${admin.name}" />
</p>
<p>
	<spring:message code="admin.surname" />
	:
	<jstl:out value="${admin.surname}" />
</p>


<spring:message code="admin.dateFormat2" var="dateFormat2" />
<p>
<spring:message code="admin.birthdate"/>:<fmt:formatDate value="${admin.birthdate}" pattern="${dateFormat2}" />
</p>


<jstl:if test='${not empty admin.phone}'>
<p>
	<spring:message code="admin.phone" />
	:
	<jstl:out value="${admin.phone}" />
</p>
</jstl:if>
<p>
	<spring:message code="admin.email" />
	:
	<jstl:out value="${admin.email}" />
</p>
<jstl:if test="${requestURI == 'admin/admin/display.do'}">
	<a href="admin/admin/edit.do"> <spring:message code="admin.edit" />
	</a>
</jstl:if>


