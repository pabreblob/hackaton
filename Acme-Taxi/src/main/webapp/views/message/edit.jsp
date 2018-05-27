<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="message/actor/edit.do" modelAttribute="messageForm">
<acme:textbox code="message.recipient" path="recipients" placeholder="username1, username2, username3"/>
<acme:textbox code="message.subject" path="subject"/>
<form:label path="priority"><spring:message code="message.priority"/></form:label>
<spring:message code="message.priolow" var="priolow"/>
<spring:message code="message.prioneutral" var="prioneutral"/>
<spring:message code="message.priohigh" var="priohigh"/>
<form:select path="priority">
<form:option label="${priolow}" value="LOW"/>
<form:option label="${prioneutral}" value="NEUTRAL"/>
<form:option label="${priohigh}" value="HIGH"/>
</form:select>
<form:errors cssClass="error" path="priority"/><br/>
<acme:textarea code="message.body" path="body"/>
<acme:submit name="save" code="message.save"/>
<acme:cancel code="message.cancel" url="/folder/actor/list.do"/>
</form:form>
