<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="service/mechanic/edit.do" modelAttribute="service">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="suspended" />
	<form:hidden path="repairShop" />
	<acme:textbox code="service.title" path="title" />
	<acme:textbox code="service.price" path="price" />
	<acme:submit name="save" code="service.save"  />
	<jstl:if test="${service.id != 0}">
	<input type="submit" name="delete"
				value="<spring:message code="service.delete" />"
				onclick="return confirm('<spring:message code="service.confirm.delete" />')" />
	</jstl:if>
	<acme:cancel code="service.cancel" url="/repairShop/mechanic/display.do?repairShopId=${service.repairShop.id}" />
</form:form>



