<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<jstl:if test="${request.cancelled}">
	<p><spring:message code="request.cancelled"/></p>
</jstl:if>
<p><spring:message code="request.origin"/>: <jstl:out value="${request.origin}"/></p>
<p><spring:message code="request.destination"/>: <jstl:out value="${request.destination}"/></p>
<spring:message code="request.momentPlaceholder" var="dateFormat"/>
<p><spring:message code="request.moment"/>: <fmt:formatDate value="${request.moment}" pattern="${dateFormat}" /></p>
<jstl:if test="${request.comment != '' and request.comment != null}">
	<p><spring:message code="request.commentNoOptional"/>: <jstl:out value="${request.comment}"/></p>
</jstl:if>

<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${not request.cancelled}">
		<a href="request/admin/delete.do?requestId=${request.id}">[<spring:message code="request.delete"/>]</a><br><br>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('DRIVER')">
	<jstl:if test="${request.moment > now and request.driver eq null and maxPass > request.passengersNumber}">
		<a href="request/driver/accept.do?requestId=${request.id}">[<spring:message code="request.accepted"/>]</a>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('DRIVER')">
	<jstl:if test="${request.moment > now and request.driver eq null}">
		<a href="request/user/edit.do?requestId=${request.id}">[<spring:message code="request.edit"/>]</a>
		<a href="request/user/delete.do?requestId=${request.id}">[<spring:message code="request.delete"/>]</a><br><br>
	</jstl:if>
	
	<jstl:if test="${request.moment > now and request.driver != null and request.cancelled eq false}">
		<a href="request/user/cancel.do?requestId=${request.id}">[<spring:message code="request.cancel"/>]</a><br><br>
	</jstl:if>
	
</security:authorize>




<iframe
  width="700"
  height="500"
  frameborder="0" style="border:0"
  src="https://www.google.com/maps/embed/v1/directions?key=&origin=<jstl:out value="${request.origin}"/>&destination=<jstl:out value="${request.destination}"/>&avoid=ferries|tolls&mode=driving" allowfullscreen>
</iframe>

<p><spring:message code="request.price"/>: <jstl:out value="${request.price}"/> <jstl:out value="${currency}"/></p>
<p><spring:message code="request.estimatedTime"/>: <jstl:out value="${estimated}"/> (<spring:message code="request.timeCanChange"/>)</p>
