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
<jstl:if test="${request.comment != '' and report.imageUrl != null}">
	<p><spring:message code="request.commentNoOptional"/>: <jstl:out value="${request.comment}"/></p>
</jstl:if>

<iframe
  width="700"
  height="500"
  frameborder="0" style="border:0"
  src="https://www.google.com/maps/embed/v1/directions?key=&origin=<jstl:out value="${request.origin}"/>&destination=<jstl:out value="${request.destination}"/>&avoid=ferries|tolls&mode=driving" allowfullscreen>
</iframe>

<p><spring:message code="request.price"/>: <jstl:out value="${request.price}"/> <jstl:out value="${currency}"/></p>
<p><spring:message code="request.estimatedTime"/>: <jstl:out value="${estimated}"/> (<spring:message code="request.timeCanChange"/>)</p>

<form:form action="request/user/saveEdit.do" modelAttribute="request">
	<form:hidden path="id"/>
	<form:hidden path="origin"/>
	<form:hidden path="destination"/>
	<form:hidden path="passengersNumber"/>
	<form:hidden path="moment"/>
	<form:hidden path="comment"/>
	<spring:message code="request.confirmSubmit"/><br><br>
	<acme:submit name="submit" code="request.submit"/>
	<acme:cancel url="/welcome/index.do" code="request.cancel"/>	
</form:form>