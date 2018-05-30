<%--
 * 
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<display:table name="reservations" id="row" requestURI="${requestURI}" keepStatus="true"
	pagesize="5" class="displaytag" sort="external" partialList="true"
	size="${total}">
<jstl:if test="${requestURI == 'reservation/user/list.do' }">

<display:column>
<jstl:if test="${!row.cancelled }">
			<a href="reservation/user/cancel.do?reservationId=${row.id}"> <spring:message
					code="reservation.cancel" />
			</a>
</jstl:if>
		</display:column>
</jstl:if>

<spring:message code="reservation.service" var="serviceHeader" />
<security:authorize access="hasRole('MECHANIC')">

<display:column title="${serviceHeader}">
			<a href="service/mechanic/display.do?serviceId=${row.service.id}"> <jstl:out value="${row.service.title}" />
			</a>

		</display:column>
</security:authorize>
<security:authorize access="hasRole('USER')">
<display:column property="service.title" title="${serviceHeader}" />
</security:authorize>

<security:authorize access="hasRole('MECHANIC')">
<spring:message code="reservation.user" var="userHeader" />
<display:column title="${userHeader}">
			<a href="user/display.do?userId=${row.user.id}"> <jstl:out value="${row.user.userAccount.username}" />
			</a>

		</display:column>
</security:authorize>

<spring:message code="reservation.dateFormat" var="dateFormatHeader" />
<spring:message code="reservation.moment" var="momentHeader" />

<display:column title="${momentHeader}" sortable="true" sortName="moment">
		<fmt:formatDate value="${row.moment}" pattern="${dateFormatHeader}" />
	</display:column>
<spring:message code="reservation.comment" var="commentHeader" />
<display:column property="comment" title="${commentHeader}" />
<spring:message code="reservation.status" var="statusHeader" />
<display:column title="${statusHeader}" >
<jstl:if test="${row.cancelled }">
<spring:message code="reservation.cancelled" />
</jstl:if>
<jstl:if test="${!row.cancelled }">
<spring:message code="reservation.pending" />
</jstl:if>
</display:column>
<spring:message code="reservation.repairShop" var="repairHeader" />
<security:authorize access="hasRole('MECHANIC')">
<display:column title="${repairHeader}">
			<a href="repairShop/mechanic/display.do?repairShopId=${row.service.repairShop.id}"> <jstl:out value="${row.service.repairShop.name}" />
			</a>

		</display:column>
</security:authorize>
<jstl:if test="${requestURI == 'reservation/user/list.do' }">
<display:column title="${repairHeader}">
			<a href="repairShop/user/display.do?repairShopId=${row.service.repairShop.id}"> <jstl:out value="${row.service.repairShop.name}" />
			</a>

		</display:column>
</jstl:if>



</display:table>




