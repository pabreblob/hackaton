<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<form:form action="request/user/save.do" modelAttribute="request">
	<spring:message code="request.giveInformation"/>
	<form:label path="origin">
		<spring:message code="request.origin" />: 
	</form:label>	
	<spring:message code="request.placeholderPlace" var="placeholderHeader"/>
	<form:input path="origin" placeholder="${placeholderHeader}" />	
	<form:errors path="origin" cssClass="error" />
	<br>
	<form:label path="destination">
		<spring:message code="request.destination" />: 
	</form:label>	
	<form:input path="destination" placeholder="${placeholderHeader}" />	
	<form:errors path="destination" cssClass="error" />
	<br>
</form:form>