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

<form:form action="reservation/user/edit.do" modelAttribute="reservation">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="service" />
	<form:hidden path="user" />
	<form:hidden path="cancelled" />
	<spring:message code="reservation.placeholderDate" var="dateplaceholder"/>
	<acme:textbox code="reservation.moment" path="moment" placeholder='${dateplaceholder}' /><br />
	<acme:textarea code="reservation.comment" path="comment" /><br />
	<acme:submit name="save" code="reservation.save"  />
	<acme:cancel code="reservation.cancel" url="/reservation/user/list.do" /><br />	
</form:form>



