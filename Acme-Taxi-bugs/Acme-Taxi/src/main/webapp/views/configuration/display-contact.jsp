<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<div>
	${contact}
</div>

<jstl:if test="${eng == true}">
	<div>
		<a href="configuration/admin/edit-contacteng.do"><spring:message code="configuration.contact.edit"/></a>
	</div>
</jstl:if>

<jstl:if test="${eng == false}">
	<div>
		<a href="configuration/admin/edit-contactesp.do"><spring:message code="configuration.contact.edit"/></a>
	</div>
</jstl:if>
