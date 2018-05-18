<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<display:table class="displaytag"  requestURI="${requestURI}" name="requests" sort="external" partialList="true" size="${total}"
	pagesize="5" id="row">
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="request.user" var="userHeader"/>
		<display:column title="${userHeader}">
			<jstl:out value="${row.user.userAccount.username}"/> (<a href="actor/display.do?actorId=${row.id}"><spring:message code="request.viewProfile"/></a>)
		</display:column>
	</security:authorize>

	<spring:message code="request.origin" var="originHeader"/>
	<display:column property="origin" title="${originHeader}"/>
	
	<spring:message code="request.destination" var="destinationHeader"/>
	<display:column property="destination" title="${destinationHeader}"/>
	
	<spring:message code="request.moment" var="momentHeader" />
	<spring:message code="request.momentPlaceholder" var="dateFormat" />
	<display:column title="${momentHeader}" sortable="true" sortName="moment">
		<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
	</display:column>
	
	<spring:message code="request.price" var="priceHeader"/>
	<display:column title="${priceHeader}">
		<jstl:out value="${row.price}"/> <jstl:out value="${currency}"/>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="request.status" var="statusHeader"/>
	<display:column title="${statusHeader}">
		<jstl:if test="${row.cancelled}">
			<spring:message code="request.cancelled"/>
		</jstl:if>
		<jstl:if test="${row.driver != null}">
			<spring:message code="request.accepted"/>
		</jstl:if>
		<jstl:if test="${row.moment > now and row.cancelled eq false and row.driver eq null}">
			<spring:message code="request.waiting"/>
		</jstl:if>
		<jstl:if test="${row.moment <= now and row.cancelled eq false and row.driver eq null}">
			<spring:message code="request.noDriver"/>
		</jstl:if>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="request.status" var="statusHeader"/>
	<display:column title="${statusHeader}">
		<jstl:if test="${row.cancelled}">
			<spring:message code="request.cancelled"/>
		</jstl:if>
		<jstl:if test="${row.driver != null}">
			<spring:message code="request.accepted"/>
		</jstl:if>
		<jstl:if test="${row.moment > now and row.cancelled eq false and row.driver eq null}">
			<spring:message code="request.waiting"/>
		</jstl:if>
		<jstl:if test="${row.moment <= now and row.cancelled eq false and row.driver eq null}">
			<spring:message code="request.noDriver"/>
		</jstl:if>
		<jstl:if test="${row.moment <= now and row.cancelled eq false and row.driver != null}">
			<spring:message code="request.finished"/>
		</jstl:if>
	</display:column>
	</security:authorize>
	
	<display:column>
		<a href="request/user/display.do?requestId=${row.id}"><spring:message code="request.display"/></a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<display:column>
		<jstl:if test="${row.driver eq null and not row.cancelled and row.moment > now}">
				<a href="request/user/edit.do?requestId=${row.id}"><spring:message code="request.edit"/></a>
		</jstl:if>
		<jstl:if test="${row.driver != null and row.cancelled eq false and row.moment > now}">
				<a href="request/user/cancel.do?requestId=${row.id}"><spring:message code="request.cancel"/></a>
		</jstl:if>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${row.driver eq null and not row.cancelled and row.moment > now}">
				<a href="request/user/delete.do?requestId=${row.id}"><spring:message code="request.delete"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:if test="${row.driver eq null and not row.cancelled and row.moment > now}">
				<a href="request/admin/delete.do?requestId=${row.id}"><spring:message code="request.delete"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('DRIVER')">
		<display:column>
			<jstl:if test="${row.driver eq null and row.moment > now}">
				<a href="request/driver/accept.do?requestId=${row.id}"><spring:message code="request.accept"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>