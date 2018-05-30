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

<display:table name="announcements" id="row" requestURI="announcement/user/finderresult.do" pagesize="5" class="displaytag">
<spring:message code="announcement.title" var="titleHeader"/> 
<display:column property="title" sortable="true" title="${titleHeader}"/>
<spring:message code="announcement.origin" var="originHeader"/> 
<display:column property="origin" sortable="true" title="${originHeader}"/>
<spring:message code="announcement.destination" var="destinationHeader"/> 
<display:column property="destination" sortable="true" title="${destinationHeader}"/>
<spring:message code="announcement.price" var="priceHeader"/> 
<display:column sortable="true" title="${priceHeader}">
	<jstl:out value="${row.pricePerPerson}"/> <jstl:out value="${currency}"/>
</display:column>
<spring:message code="announcement.moment" var="momentHeader" />
<spring:message code="moment.date.format" var="dateFormat" />
<display:column sortable="true" title="${momentHeader}">
	<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
</display:column>
<spring:message code="announcement.pets" var="petsHeader"/>
<display:column title="${petsHeader}">
<jstl:if test="${!row.petsAllowed}">
	<spring:message code="announcement.no"/>
</jstl:if>
<jstl:if test="${row.petsAllowed}">
	<spring:message code="announcement.yes"/>
</jstl:if>
</display:column>
<spring:message code="announcement.smoking" var="smokingHeader"/>
<display:column title="${smokingHeader}">
<jstl:if test="${!row.smokingAllowed}">
	<spring:message code="announcement.no"/>
</jstl:if>
<jstl:if test="${row.smokingAllowed}">
	<spring:message code="announcement.yes"/>
</jstl:if>
</display:column>
<spring:message code="announcement.cancelled" var="cancelledHeader"/>
<display:column title="${cancelledHeader}">
<jstl:if test="${!row.cancelled}">
	<spring:message code="announcement.no"/>
</jstl:if>
<jstl:if test="${row.cancelled}">
	<spring:message code="announcement.yes"/>
</jstl:if>
</display:column>
<display:column>
	<a href="announcement/user/display.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
</display:column>
</display:table>