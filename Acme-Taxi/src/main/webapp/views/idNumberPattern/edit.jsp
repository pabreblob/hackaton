<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="idNumberPattern/save.do" modelAttribute="form">
	<form:hidden path="id"/>
	<acme:textbox code="idNumberPattern.pattern" path="text"/>
	
	<form:label path="nationality">
		<spring:message code="idNumberPattern.nationality" />:
	</form:label>	
	<form:select path="nationality">
	<spring:message code="idNumberPattern.selectOne" var="selectOne"/>
		<form:option value="" label="${selectOne}"/>
		<form:options items="${nationalities}"/>
	</form:select>
	<form:errors path="nationality" cssClass="error" />
	<br>
	<acme:submit name="save" code="idNumberPattern.submit"/>
	<acme:cancel url="idNumberPattern/list.do" code="idNumberPattern.cancel"/>
</form:form>