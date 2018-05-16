<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${justOne}">
	<spring:message code="report.reportList"/><jstl:out value="${nameWriter}"/>
</jstl:if>

<display:table class="displaytag"  requestURI="${requestURI}" name="reports" sort="external" partialList="true" size="${total}"
	pagesize="5" id="row">
	
	<jstl:if test="${not justOne}">
		<spring:message code="report.creator" var="creatorHeader"/>
		<display:column title="${creatorHeader}" sortable="true" sortName="creator.userAccount.username">
			<jstl:out value="${row.creator.userAccount.username}"/> (<a href="actor/display.do?actorId=${row.creator.id}"><spring:message code="report.viewProfile"/></a>)
		</display:column>	
	</jstl:if>
	
	<spring:message code="report.reported" var="reportedHeader"/>
	<display:column title="${reportedHeader}" sortable="true" sortName="reported.userAccount.username">
		<jstl:out value="${row.reported.userAccount.username}"/> (<a href="actor/display.do?actorId=${row.reported.id}"><spring:message code="report.viewProfile"/></a>)
	</display:column>	
	
	<spring:message code="report.moment" var="momentHeader" />
	<spring:message code="report.format" var="dateFormat" />
	<display:column title="${momentHeader}" sortable="true" sortName="moment">
		<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
	</display:column>
	
	<spring:message code="report.display" var="displayHeader"/>
	<display:column title="${displayHeader}">
		<a href="report/admin/display.do?reportId=${row.id}"><spring:message code="report.display"/></a>	
	</display:column>
</display:table>