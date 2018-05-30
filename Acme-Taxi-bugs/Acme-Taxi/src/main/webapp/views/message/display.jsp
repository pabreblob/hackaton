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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="message.sender" />: <jstl:out value="${mess.sender.userAccount.username}"/></p>
<p><spring:message code="message.subject" />: <jstl:out value="${mess.subject}"/></p>
<spring:message code="moment.date.format" var="dateFormat" />
<p><spring:message code="message.moment" />: <fmt:formatDate value="${mess.moment}" pattern="${dateFormat}"/></p>
<jstl:if test="${mess.priority == 'LOW'}">
<p><spring:message code="message.priority" />: <spring:message code="message.priolow"/></p>
</jstl:if>
<jstl:if test="${mess.priority == 'NEUTRAL'}">
<p><spring:message code="message.priority" />: <spring:message code="message.prioneutral"/></p>
</jstl:if>
<jstl:if test="${mess.priority == 'HIGH'}">
<p><spring:message code="message.priority" />: <spring:message code="message.priohigh"/></p>
</jstl:if>
<p><spring:message code="message.body" />: <jstl:out value="${mess.body}"/></p>
<br />

<a href="message/actor/delete.do?messageId=${mess.id}"><spring:message code="message.delete"/></a>
<a href="message/actor/move.do?messageId=${mess.id}"><spring:message code="message.move"/></a>