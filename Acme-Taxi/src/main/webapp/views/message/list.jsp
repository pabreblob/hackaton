<%--
 * list.jsp
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

<h1><jstl:out value="${folder.name}"/></h1>
<display:table name="messages" id="row" requestURI="message/actor/list.do" pagesize="5" class="displaytag" sort="external" partialList="true" size="${total}">
<spring:message code="message.moment" var="momentHeader" />
<spring:message code="moment.date.format" var="dateFormat" />
<display:column sortable="true" title="${momentHeader}" sortName="moment">
<jstl:if test="${!row.checked}">
	<b><fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/></b>
</jstl:if>
<jstl:if test="${row.checked}">
	<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
</jstl:if>
</display:column>
<spring:message code="message.sender" var="senderHeader"/>
<display:column title="${senderHeader}" sortable="true" sortName="sender.userAccount.username">
<jstl:if test="${!row.checked}">
	<b><jstl:out value="${row.sender.userAccount.username}"/></b>
</jstl:if>
<jstl:if test="${row.checked}">
	<jstl:out value="${row.sender.userAccount.username}"/>
</jstl:if>
</display:column>
<spring:message code="message.subject" var="subjectHeader"/>
<display:column title="${subjectHeader}" sortable="false">
<jstl:if test="${!row.checked}">
	<b><jstl:out value="${row.subject}"/></b>
</jstl:if>
<jstl:if test="${row.checked}">
	<jstl:out value="${row.subject}"/>
</jstl:if>
</display:column>
<display:column sortable="false">
<a href="message/actor/display.do?messageId=${row.id}"><spring:message code="message.display"/></a>
</display:column>
<display:column sortable="false">
<a href="message/actor/delete.do?messageId=${row.id}"><spring:message code="message.delete"/></a>
</display:column>
<display:column sortable="false">
<a href="message/actor/move.do?messageId=${row.id}"><spring:message code="message.move"/></a>
</display:column>
<display:column sortable="false">
<jstl:if test="${!row.checked}">
<a href="message/actor/setchecked.do?messageId=${row.id}"><spring:message code="message.setchecked"/></a>
</jstl:if>
</display:column>
</display:table>
<a href="message/actor/create.do"><spring:message code="message.create" /></a>
<security:authorize access="hasRole('ADMIN')">
<a href="message/admin/create.do"><spring:message code="message.broadcast"/></a>
</security:authorize>
