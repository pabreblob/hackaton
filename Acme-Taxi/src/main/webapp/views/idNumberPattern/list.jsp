<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<a href="idNumberPattern/create.do"><spring:message code="idNumberPattern.create"/></a>
<br>
<jstl:if test="${nationalityFilter != null}">
	<h3><spring:message code="idNumberPattern.filteringBy"/><jstl:out value="${nationalityFilter}"/></h3>
</jstl:if>
<jstl:if test="${nationalityFilter == null}">
	<h3><spring:message code="idNumberPattern.allNationalities"/></h3>
</jstl:if>
<form:form action="idNumberPattern/filteredList.do" modelAttribute="stringForm">
	<form:label path="text">
		<spring:message code="idNumberPattern.nationality" />:
	</form:label>	
	<form:select path="text">
		<spring:message code="idNumberPattern.showAll" var="showAll"/>
		<form:option value="-" label="${showAll}" />		
		<form:options items="${nationalities}"/>
	</form:select>
	<acme:submit name="submit" code="idNumberPattern.submit"/>
</form:form>
<display:table class="displaytag" name="idNumberPatterns" id="row">	
	<spring:message code="idNumberPattern.nationality" var="idNumberPattern" />
	<display:column property="nationality" title="${idNumberPattern}"/>

	<spring:message code="idNumberPattern.pattern" var="pattern" />
	<display:column property="pattern" title="${pattern}"/>
	
</display:table>