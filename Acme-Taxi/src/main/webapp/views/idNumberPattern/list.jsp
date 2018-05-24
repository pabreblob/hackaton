<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

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
		<form:option value="" label="${showAll}" />		
		<form:options items="${nationalities}"/>
	</form:select>
	<acme:submit name="submit" code="idNumberPattern.submit"/><acme:button url="idNumberPattern/create.do" code="idNumberPattern.create"/>
</form:form>
<display:table class="displaytag" requestURI="idNumberPattern/list.do" name="idNumberPatterns" id="row" 
	pagesize="5" sort="external" partialList="true" size="${total}">	
	<spring:message code="idNumberPattern.nationality" var="idNumberPattern" />
	<display:column property="nationality"  sortable="true" sortName="nationality" title="${idNumberPattern}"/>

	<spring:message code="idNumberPattern.pattern" var="pattern" />
	<display:column property="pattern" title="${pattern}"/>
	
	<spring:message code="idNumberPattern.edit" var="editHeader"/>
	<display:column title="${editHeader}">
			<a href="idNumberPattern/edit.do?idNumberPatternId=${row.id}"><spring:message code="idNumberPattern.edit"/> </a>
	</display:column>

	<spring:message code="idNumberPattern.delete" var="deleteHeader"/>
	<display:column title="${deleteHeader}">
			<a href="idNumberPattern/delete.do?idNumberPatternId=${row.id}"><spring:message code="idNumberPattern.delete"/> </a>
	</display:column>
</display:table>
