<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${requestURI == 'announcement/user/list-created.do'}">
	<acme:button url="announcement/user/create.do" code="announcement.create"/>
</jstl:if>
<jstl:if test="${requestURI == 'announcement/user/list.do'}">
	<acme:button url="announcement/user/finder.do" code="announcement.use.finder"/>
</jstl:if>


<display:table name="announcements" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" sort="external" partialList="true"
	size="${total}">
	<spring:message code="announcement.title" var="titleHeader" />
	<display:column property="title" sortable="true" sortName="title"
		title="${titleHeader}" />
	<spring:message code="announcement.origin" var="originHeader" />
	<display:column property="origin" sortable="true" sortName="origin"
		title="${originHeader}" />
	<spring:message code="announcement.destination" var="destinationHeader" />
	<display:column property="destination" sortable="true"
		sortName="destination" title="${destinationHeader}" />
	<spring:message code="announcement.price" var="priceHeader" />
	<display:column sortable="true" sortName="pricePerPerson"
		title="${priceHeader}">
		<jstl:out value="${row.pricePerPerson}" />
		<jstl:out value="${currency}" />
	</display:column>
	<spring:message code="announcement.moment" var="momentHeader" />
	<spring:message code="moment.date.format" var="dateFormat" />
	<display:column sortable="true" title="${momentHeader}"
		sortName="moment">
		<fmt:formatDate value="${row.moment}" pattern="${dateFormat}" />
	</display:column>
	<spring:message code="announcement.pets" var="petsHeader" />
	<display:column title="${petsHeader}">
		<jstl:if test="${!row.petsAllowed}">
			<spring:message code="announcement.no" />
		</jstl:if>
		<jstl:if test="${row.petsAllowed}">
			<spring:message code="announcement.yes" />
		</jstl:if>
	</display:column>
	<spring:message code="announcement.smoking" var="smokingHeader" />
	<display:column title="${smokingHeader}">
		<jstl:if test="${!row.smokingAllowed}">
			<spring:message code="announcement.no" />
		</jstl:if>
		<jstl:if test="${row.smokingAllowed}">
			<spring:message code="announcement.yes" />
		</jstl:if>
	</display:column>
	<spring:message code="announcement.cancelled" var="cancelledHeader" />
	<display:column title="${cancelledHeader}">
		<jstl:if test="${!row.cancelled}">
			<spring:message code="announcement.no" />
		</jstl:if>
		<jstl:if test="${row.cancelled}">
			<spring:message code="announcement.yes" />
		</jstl:if>
	</display:column>
	<jstl:if test="${requestURI == 'announcement/user/list-created.do'}">
		<display:column>
			<a
				href="announcement/user/display-created.do?announcementId=${row.id}"><spring:message
					code="announcement.display" /></a>
		</display:column>
		<display:column>
			<jstl:if
				test="${empty row.attendants and row.moment > currentDate and !row.cancelled}">
				<a href="announcement/user/edit.do?announcementId=${row.id}"><spring:message
						code="announcement.edit" /></a>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${!row.cancelled and row.moment > currentDate}">
				<a href="announcement/user/cancel.do?announcementId=${row.id}"><spring:message
						code="announcement.cancel" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI == 'announcement/user/list-joined.do'}">
		<display:column>
			<a href="announcement/user/display.do?announcementId=${row.id}"><spring:message
					code="announcement.display" /></a>
		</display:column>
		<display:column>
			<jstl:if test="${row.moment > currentDate and !row.cancelled}">
				<a href="announcement/user/dropout.do?announcementId=${row.id}"><spring:message
						code="announcement.dropout" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI == 'announcement/user/list.do'}">
		<display:column>
			<a href="announcement/user/display.do?announcementId=${row.id}"><spring:message
					code="announcement.display" /></a>
		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI == 'announcement/list.do'}">
		<display:column>
			<a href="announcement/display.do?announcementId=${row.id}"><spring:message
					code="announcement.display" /></a>
		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI == 'announcement/admin/list.do'}">
		<spring:message code="announcement.marked" var="markedHeader" />
		<display:column title="${markedHeader}">
			<jstl:if test="${!row.marked}">
				<spring:message code="announcement.no" />
			</jstl:if>
			<jstl:if test="${row.marked}">
				<spring:message code="announcement.yes" />
			</jstl:if>
		</display:column>
		<display:column>
			<a href="announcement/admin/display.do?announcementId=${row.id}"><spring:message
					code="announcement.display" /></a>
		</display:column>
	</jstl:if>
</display:table>
