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
			<a href="reservation/user/cancel.do?reservationId=${row.id}"> <spring:message
					code="reservation.cancel" />
			</a>

		</display:column>
</jstl:if>
<security:authorize access="hasRole('MECHANIC')">
<spring:message code="reservation.user" var="userHeader" />
<display:column property="user.userAccount.username" title="${userHeader}" />
</security:authorize>
<spring:message code="reservation.dateFormat" var="dateFormatHeader" />
<spring:message code="reservation.moment" var="momentHeader" />
<display:column title="${dateHeader}">
		<fmt:formatDate value="${n.moment}" pattern="${dateFormatHeader}" />
	</display:column>
<display:column property="cancelled" sortable="true" sortName="cancelled" />
<spring:message code="reservation.repairShop" var="repairHeader" />
<display:column property="service.repairShop.name" title="${repairHeader}" sortable="true" sortName="service.repairShop.name"/>
<security:authorize access="hasRole('MECHANIC')">
<display:column>
			<a href="repairShop/mechanic/display.do?repairShopId=${row.service.repairShop.id}"> <jstl:out value="${row.service.repairShop.name}" />
			</a>

		</display:column>
</security:authorize>
<security:authorize access="hasRole('USER')">
<display:column>
			<a href="repairShop/user/display.do?repairShopId=${row.service.repairShop.id}"> <jstl:out value="${row.service.repairShop.name}" />
			</a>

		</display:column>
</security:authorize>



</display:table>




